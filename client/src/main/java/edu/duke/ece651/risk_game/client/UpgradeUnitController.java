package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionStatus;
import edu.duke.ece651.risk_game.shared.UpgradeUnitRequest;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.io.IOException;

import static java.lang.Math.max;

public class UpgradeUnitController extends GameController{

    @FXML
    ComboBox<String> currentLevelComboBox;
    @FXML
    ComboBox<String> targetLevelComboBox;
    @FXML
    Slider unitSlider;
    @FXML
    Label unitLabel;
    public void initialize() {
        super.initialize();
        for (int i = 0; i <= 6; i++) {
            currentLevelComboBox.getItems().add("Level " + i);
            if (i >= 1) {
                targetLevelComboBox.getItems().add("Level " + i);
            }
        }
        if (gameContext.finalClickedTerritoryID != -1) {
            clickList[gameContext.finalClickedTerritoryID].run();
        }
        unitSlider.setMin(0);
        unitSlider.setMax(100);
        unitSlider.setValue(0);
        unitSlider.setBlockIncrement(1);
        unitSlider.setSnapToTicks(true);
        unitSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            unitLabel.setText(String.valueOf(newValue.intValue()));
        });
    }

    @FXML
    public void handleUpgradeButton() {
        if (gameContext.finalClickedTerritoryID == -1) {
            gameContext.showErrorAlert("Error", "Please click on the map to choose the territory to operate on.");
        } else if (gameContext.territories.get(gameContext.finalClickedTerritoryID).getOwner() != gameContext.playerIDMap.get(gameContext.currentRoomID)) {
            gameContext.showErrorAlert("Error", "You could only upgrade units of your own territory.");
        } else {
            int territory = gameContext.finalClickedTerritoryID;
            int currentLevel = Integer.parseInt(currentLevelComboBox.getValue().substring(6));
            int targetLevel = Integer.parseInt(targetLevelComboBox.getValue().substring(6));
            int amount = (int) unitSlider.getValue();
            UpgradeUnitRequest request = new UpgradeUnitRequest(gameContext.playerIDMap.get(gameContext.currentRoomID),
                    territory, currentLevel, max(targetLevel - currentLevel, 0), amount);
            ActionStatus status;
            try {
                status = gameContext.httpClient.sendUpgradeUnit(gameContext.currentRoomID, request);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Please input again!");
                return;
            }
            if (!status.isSuccess()) {
                gameContext.showErrorAlert("Your Upgrade command is invalid.", status.getErrorMessage());
            } else {
                sceneManager.switchTo("GameMain.fxml");
            }
        }
    }

    @FXML
    public void handleCancelButton() {
        sceneManager.switchTo("GameMain.fxml");
    }
}
