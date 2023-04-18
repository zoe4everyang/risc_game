package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionRequest;
import edu.duke.ece651.risk_game.shared.ActionStatus;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.HashMap;

public class AttackController extends GameController{
    public void initialize() {

    }

    @FXML
    public void handleAttackButton() {
        // TODO: get the input from the text field
        String[] command = {"1", "2", "3", "4"};
        HashMap<String, Integer> territoryIDMap = gameContext.territoryIDMaps.get(gameContext.currentRoomID);
        int from = territoryIDMap.get(command[0]);
        int to = territoryIDMap.get(command[1]);
        ArrayList<Integer> unitIDs = new ArrayList<>();
        for (int i = 3; i < command.length; i++) {
            unitIDs.add(Integer.parseInt(command[i]));
        }
        ActionRequest request = new ActionRequest(gameContext.playerIDMap.get(gameContext.currentRoomID), from, to, unitIDs);
        ActionStatus status = gameContext.httpClient.sendAttack(gameContext.currentRoomID, request);
        if (!status.isSuccess()) {
            throw new IllegalArgumentException(status.getErrorMessage());
        } else {
            sceneManager.switchTo("Game.fxml");
        }
    }
}
