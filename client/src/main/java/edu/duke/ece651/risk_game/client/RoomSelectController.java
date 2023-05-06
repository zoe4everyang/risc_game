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
            gameContext.showErrorAlert("Failed to get room list", "Error while getting room list: " + e.getMessage());
        }
    }

    @FXML
    public void handleExitButton() throws IOException {
        Platform.exit();
    }
    @FXML
    public void handleJoinButton() throws IOException {
        // room ID input

        String roomIDString = roomList.getSelectionModel().getSelectedItem();
        if (roomIDString == null) {
            gameContext.showErrorAlert("Error", "Please choose a room ID to join.");
            return;
        }

        Integer roomID = Integer.parseInt(roomIDString);
        if (!roomIDs.contains(roomID)) {
            gameContext.showErrorAlert("Error", "Please choose a valid room ID.");
            sceneManager.switchTo("RoomSelect.fxml");
        }

        // join room
        Response response = null;
        //Stage loadingStage = sceneManager.createNewWindow("Loading.fxml");
        try {
            response = gameContext.httpClient.joinRoom(gameContext.username, roomID);
        } catch (IOException e) {
            System.out.println("Error while sending join room request: " + e.getMessage());
        }
        //loadingStage.close();

        if (response == null) {
            sceneManager.switchTo("RoomSelect.fxml");
        }

        // update room Info
        assert response != null;
        gameContext.currentRoomID = roomID;
        if (!gameContext.playerIDMap.containsKey(roomID)) {
            if (response.getPlayerInfo() != null) {
                System.out.println("The info is not null!!!!");
            }
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
