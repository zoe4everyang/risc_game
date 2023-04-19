package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionStatus;
import edu.duke.ece651.risk_game.shared.Territory;
import edu.duke.ece651.risk_game.shared.UpgradeUnitRequest;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;

import static java.lang.Math.max;

public class UpgradeUnitController extends GameController{
    @FXML
    ComboBox<String> territoryComboBox;
    @FXML
    ComboBox<String> currentLevelComboBox;
    @FXML
    ComboBox<String> targetLevelComboBox;
    public void initialize() {
        super.initialize();
        for (Territory territory : gameContext.territories) {
            if (territory.getOwner() == gameContext.playerIDMap.get(gameContext.currentRoomID)) {
                territoryComboBox.getItems().add(territory.getName());
            }
        }
        for (int i = 0; i <= 6; i++) {
            currentLevelComboBox.getItems().add("Level " + i);
            if (i >= 1) {
                targetLevelComboBox.getItems().add("Level " + i);
            }
        }
    }

    @FXML
    public void handleUpgradeButton() {
        int territory = gameContext.territoryIDMaps.get(gameContext.currentRoomID).get(territoryComboBox.getValue());
        int currentLevel = Integer.parseInt(currentLevelComboBox.getValue().substring(6));
        int targetLevel = Integer.parseInt(targetLevelComboBox.getValue().substring(6));
        UpgradeUnitRequest request = new UpgradeUnitRequest(gameContext.playerIDMap.get(gameContext.currentRoomID),
                territory, currentLevel, max(targetLevel - currentLevel, 0));
        ActionStatus status;
        try {
            status = gameContext.httpClient.sendUpgradeUnit(gameContext.currentRoomID, request);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Please input again!");
            return;
        }
        if (!status.isSuccess()) {
            gameContext.showErrorAlert("Your Upgrade command is invalid.",status.getErrorMessage());
        } else {
            sceneManager.switchTo("GameMain.fxml");
        }
    }
}
