package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.*;
import javafx.scene.control.Alert;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

public class GameContext {

    public final RISCClient httpClient;
    public String username;
    public Integer currentRoomID;
    public PlayerInfo playerInfo;
    public final HashMap<Integer, Integer> playerIDMap;
    public List<String> playerList;
    public List<Territory> territories;
    public Integer unitAvailable;
    public Boolean failTheGame;
    public final HashMap<Integer, HashMap<Integer, String>> territoryNameMaps;
    public final HashMap<Integer, HashMap<String, Integer>> territoryIDMaps;
    public final HashMap<String, BiFunction<Integer, ActionRequest, ActionStatus>> actionFns;
    public Alert errorAlert;
    public Alert infoAlert;
    public List<String> colorList;
    public Integer finalClickedTerritoryID;
    public HashMap<String, Boolean> clickHistory;

    /**
     * Constructor for GameContext
     */
    public GameContext() {
        this.httpClient = new RISCClient();
        this.username = "";
        this.currentRoomID = -1;
        this.playerIDMap = new HashMap<>();
        this.unitAvailable = -1;
        this.failTheGame = false;
        this.territoryNameMaps = new HashMap<>();
        this.territoryIDMaps = new HashMap<>();
        this.actionFns = new HashMap<>();
        initActionFns();
        this.errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        this.infoAlert = new Alert(Alert.AlertType.INFORMATION);
        this.colorList = List.of("#9966CC", "#6495ED", "#BC8F8F", "#00FF7F", "#000000", "#C9B0E3", "#9EB9EA", "#D8CACA", "#AAE9C9", "#BEBEBE");
        this.finalClickedTerritoryID = -1;
        this.clickHistory = new HashMap<>();
        initClickedHistory();
    }

    private void initActionFns() {
        actionFns.put("A", httpClient::sendAttack);
        actionFns.put("M", httpClient::sendMove);
    }

    private void initClickedHistory() {
        this.clickHistory.put("upgradeTech", false);
        this.clickHistory.put("spy", false);
        this.clickHistory.put("cloak", false);
    }
    private static final class InstanceHolder {
        private static final GameContext instance = new GameContext();
    }

    public static GameContext getInstance() {
        return GameContext.InstanceHolder.instance;
    }
    public void update(Response response) {
        this.playerInfo = response.getPlayerInfo();
        this.playerList = response.getPlayerList();
        this.unitAvailable = response.getUnitAvailable();
        this.failTheGame = response.isLose();
        List<Territory> srcs = response.getTerritories();
        if (this.territories == null) {
            this.territories = srcs;
        } else {
            PlayerInfo curPlayerInfo = this.playerInfo;
            for (int i = 0; i < srcs.size(); i++){
                if (curPlayerInfo.getVisible().get(i)) {
                    this.territories.set(i, srcs.get(i));
                }
            }
        }
    }

    public void showErrorAlert(String header, String content) {
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }

    public void showInfoAlert(String header, String content) {
        infoAlert.setHeaderText(header);
        infoAlert.setContentText(content);
        infoAlert.showAndWait();
    }
}
