package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.PlacementRequest;
import edu.duke.ece651.risk_game.shared.Response;
import edu.duke.ece651.risk_game.shared.Territory;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PlacementController extends UIController {
    private ArrayList<Integer> placement;
    //private ArrayList<Integer> territoryIDs;
    private Integer unitLeft;
    @FXML
    TextFlow unitLeftText;
    @FXML
    ComboBox<String> territoryComboBox;
    @FXML
    TextField unitTextField;
    @FXML
    Polygon territory0;
    @FXML
    Polygon territory1;
    @FXML
    Polygon territory2;
    @FXML
    Polygon territory3;
    @FXML
    Polygon territory4;
    @FXML
    Polygon territory5;
    @FXML
    Polygon territory6;
    @FXML
    Polygon territory7;
    @FXML
    Polygon territory8;
    @FXML
    Polygon territory9;
    @FXML
    Polygon territory10;
    @FXML
    Polygon territory11;
    List<Polygon> territoryList;

    public PlacementController() {
        super();
    }

    public void initialize() {
        //territoryIDs = new ArrayList<>();
        ArrayList<String> territoryNames = new ArrayList<>();
        placement = new ArrayList<>(Collections.nCopies(gameContext.territoryNameMaps.get(gameContext.currentRoomID).size(), -1));
        for (Territory territory : gameContext.territories) {
            if (territory.getOwner() == gameContext.playerInfo.getPlayerID()) {
                //territoryIDs.add(territory.getID());
                territoryNames.add(territory.getName());
                placement.set(territory.getID(), 0);
            }
        }

        unitLeft = gameContext.unitAvailable;
        Text text = new Text(unitLeft.toString());
//        text.setFont(Font.font("Palatino Linotype", FontWeight.NORMAL, 14));
//        text.setStyle("-fx-fill: d4b91d;");
        unitLeftText.getChildren().add(text);

        for (String territoryName : territoryNames) {
            territoryComboBox.getItems().add(territoryName);
        }
        unitTextField.setTextFormatter(integerFormatter);

        territoryList = new ArrayList<>();
        territoryList.add(territory0);
        territoryList.add(territory1);
        territoryList.add(territory2);
        territoryList.add(territory3);
        territoryList.add(territory4);
        territoryList.add(territory5);
        territoryList.add(territory6);
        territoryList.add(territory7);
        territoryList.add(territory8);
        territoryList.add(territory9);
        territoryList.add(territory10);
        territoryList.add(territory11);
        for (int i = 0; i < territoryList.size(); i++) {
            if (gameContext.territories.get(i).getOwner() == gameContext.playerInfo.getPlayerID()) {
                territoryList.get(i).setFill(Color.web("#D4B91D"));
            } else {
                territoryList.get(i).setFill(Color.web("#A0A0A0"));
            }
        }

        gameContext.showInfoAlert("Placement Phase", "You have " + gameContext.unitAvailable + " units to place.");
    }

    @FXML
    public void handlePlaceButton() throws IOException {
        String territoryName = territoryComboBox.getValue();
        if (territoryName == null) {
            gameContext.showErrorAlert("Invalid Input", "Please select a territory.");
            return;
        }
        int num = Integer.parseInt(unitTextField.getText());
        if (num > unitLeft) {
            gameContext.showErrorAlert("Invalid Input", "You do not have enough units to place.");
        } else {
//            Text testText3 = new Text(Integer.toString(gameContext.currentRoomID));
//            unitLeftText.getChildren().add(testText3);
            unitLeftText.getChildren().clear();
            HashMap<String, Integer> index1 = gameContext.territoryIDMaps.get(gameContext.currentRoomID);
            int index2 = index1.get(territoryName);
            int cur = placement.get(index2);
            placement.set(index2, cur + num);
            unitLeft -= num;
            Text text = new Text(unitLeft.toString());
//            text.setFont(Font.font("Palatino Linotype", FontWeight.NORMAL, 14));
//            text.setStyle("-fx-fill: d4b91d;");

            unitLeftText.getChildren().add(text);
        }
    }

    @FXML
    public void handleFinishButton() {
        if (unitLeft != 0) {
            gameContext.showErrorAlert("Invalid Input", "You have not placed all your units.");
            //sceneManager.switchTo("Place.fxml");
            return;
        }
        PlacementRequest placementRequest = new PlacementRequest(gameContext.playerInfo.getPlayerID(), placement);
        Response response = null;
        //Stage loadingStage = sceneManager.createNewWindow("Loading.fxml");
        try {
            response = gameContext.httpClient.sendPlacement(gameContext.currentRoomID, placementRequest);
        } catch (IOException e) {
            gameContext.showErrorAlert("Failed to send placement request", "Error while sending placement request: " + e.getMessage());
        }
        //loadingStage.close();
        assert response != null;
        gameContext.update(response);
        sceneManager.switchTo("GameMain.fxml");
    }
}
