package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.Response;
import edu.duke.ece651.risk_game.shared.Territory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoomSelectController extends UIController{
    @FXML
    ListView<String> roomList;
    ObservableList<String> roomListData;
    HashSet<Integer> roomIDs;
    public RoomSelectController() {
        super();
        roomIDs = null;
    }

    public void initialize() {
        // get room ID list
        try {
            roomIDs = gameContext.httpClient.getRoomList(GameContext.getInstance().username);
            Stream<String> stream = roomIDs.stream().map(Object::toString);
            roomListData = FXCollections.observableArrayList(stream.collect(Collectors.toList()));
            roomList.setItems(roomListData);
        } catch (IOException e) {
            gameContext.errorAlert.setHeaderText("Failed to get room list");
            gameContext.errorAlert.setContentText("Error while getting room list: " + e.getMessage());
            gameContext.errorAlert.showAndWait();
        }
    }

    @FXML
    public void handleExitButton() throws IOException {
        Platform.exit();
    }
    @FXML
    public void handleJoinButton() throws IOException {
        // room ID input
        Integer roomID;
        roomID = Integer.parseInt(roomList.getSelectionModel().getSelectedItem());
        if (!roomIDs.contains(roomID)) {
            gameContext.errorAlert.setHeaderText("Failed to get room list");
            gameContext.errorAlert.setContentText("Please choose a valid room ID");
            gameContext.errorAlert.showAndWait();
            sceneManager.switchTo("RoomSelect.fxml");
        }

        // join room
        Response response = null;
        try {
            response = gameContext.httpClient.joinRoom(gameContext.username, roomID);
        } catch (IOException e) {
            System.out.println("Error while sending join room request: " + e.getMessage());
        }
        if (response == null) {
            sceneManager.switchTo("RoomSelect.fxml");
        }

        // update room Info
        assert response != null;
        gameContext.currentRoomID = roomID;
        if (!gameContext.playerIDMap.containsKey(roomID)) {
            gameContext.playerIDMap.put(roomID, response.getPlayerInfo().getPlayerID());
        }
        if (!gameContext.territoryNameMaps.containsKey(roomID)) {
            HashMap<Integer, String> territoryNameMap = new HashMap<>();
            HashMap<String, Integer> territoryIDMap = new HashMap<>();
            List<Territory> territories = response.getTerritories();
            for (Territory territory : territories) {
                territoryNameMap.put(territory.getID(), territory.getName());
                territoryIDMap.put(territory.getName(), territory.getID());
            }
            gameContext.territoryNameMaps.put(roomID, territoryNameMap);
            gameContext.territoryIDMaps.put(roomID, territoryIDMap);
        }
        gameContext.update(response);

        // switch to next stage
        if (response.getUnitAvailable() == -1) {
            sceneManager.switchTo("GameMain.fxml");
        } else {
            sceneManager.switchTo("Place.fxml");
        }
    }
}
