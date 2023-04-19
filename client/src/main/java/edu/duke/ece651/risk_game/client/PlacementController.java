package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.PlacementRequest;
import edu.duke.ece651.risk_game.shared.Response;
import edu.duke.ece651.risk_game.shared.Territory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PlacementController extends UIController {
    private ArrayList<Integer> placement;
    private ArrayList<Integer> territoryIDs;
    private Integer unitLeft;
    @FXML
    TextFlow unitLeftText;
    @FXML
    ComboBox<String> territoryComboBox;
    @FXML
    TextField unitTextField;
    @FXML
    Button place;
    @FXML
    Button finish;

    public PlacementController() {
        super();
    }

    public void initialize() {
        territoryIDs = new ArrayList<>();
        ArrayList<String> territoryNames = new ArrayList<>();
        placement = new ArrayList<>(Collections.nCopies(gameContext.territoryNameMaps.get(gameContext.currentRoomID).size(), -1));
        for (Territory territory : gameContext.territories) {
            if (territory.getOwner() == gameContext.playerInfo.getPlayerID()) {
                territoryIDs.add(territory.getID());
                territoryNames.add(territory.getName());
                placement.set(territory.getID(), 0);
            }
        }

        unitLeft = gameContext.unitAvailable;
        Text text = new Text(unitLeft.toString());
        text.setStyle("-fx-font-weight: bold;");
        unitLeftText.getChildren().add(text);

        for (String territoryName : territoryNames) {
            territoryComboBox.getItems().add(territoryName);
        }
        unitTextField.setTextFormatter(integerFormatter);
        gameContext.showInfoAlert("Placement Phase", "You have " + gameContext.unitAvailable + " units to place.");
    }

    @FXML
    public void handlePlaceButton() throws IOException {
        String territoryName = territoryComboBox.getValue();
        int num = Integer.parseInt(unitTextField.getText());
        if (num > unitLeft) {
            gameContext.showErrorAlert("Invalid Input", "You do not have enough units to place.");
        } else {
            int index = territoryIDs.get(gameContext.territoryIDMaps.get(gameContext.currentRoomID).get(territoryName));
            int cur = placement.get(index);
            placement.set(index, cur + num);
            unitLeft -= num;
            Text text = new Text(unitLeft.toString());
            text.setStyle("-fx-font-weight: bold;");
            unitLeftText.getChildren().add(text);
        }
    }

    @FXML
    public void handleFinishButton() {
        if (unitLeft != 0) {
            gameContext.showErrorAlert("Invalid Input", "You have not placed all your units.");
            sceneManager.switchTo("Place.fxml");
        }
        PlacementRequest placementRequest = new PlacementRequest(gameContext.playerInfo.getPlayerID(), placement);
        Response response = null;
        try {
            response = gameContext.httpClient.sendPlacement(gameContext.currentRoomID, placementRequest);
        } catch (IOException e) {
            gameContext.showErrorAlert("Failed to send placement request", "Error while sending placement request: " + e.getMessage());
        }
        assert response != null;
        gameContext.update(response);
        sceneManager.switchTo("GameMain.fxml");
    }
}
