package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionStatus;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class UpgradeSpyController extends GameController{

    @FXML
    ComboBox<String> unitLevelComboBox;


    public void initialize() {
        super.initialize();
        for (int i = 0; i <= 6; i++) {
            unitLevelComboBox.getItems().add("Level " + i);
        }
        if (gameContext.finalClickedTerritoryID != -1) {
            clickList[gameContext.finalClickedTerritoryID].run();
        }
    }

    @FXML
    public void handleUpgradeButton() throws IOException {
        if (gameContext.finalClickedTerritoryID == -1) {
            gameContext.showErrorAlert("Error", "Please click on the map to choose the territory to operate on.");
        } else if (gameContext.territories.get(gameContext.finalClickedTerritoryID).getOwner() != gameContext.playerIDMap.get(gameContext.currentRoomID)) {
            gameContext.showErrorAlert("Error", "You could only upgrade spy on your own territory.");
        } else {
            int level = Integer.parseInt(unitLevelComboBox.getValue().substring(6));
            System.out.println("level is :" + level);
            ActionStatus status = gameContext.httpClient.sendUpgradeSpy(gameContext.currentRoomID, gameContext.playerIDMap.get(gameContext.currentRoomID),
                    gameContext.finalClickedTerritoryID, Integer.parseInt(unitLevelComboBox.getValue().substring(6)));
            if (!status.isSuccess()) {
                gameContext.showErrorAlert("Error", status.getErrorMessage() + "You don't have enough tech level or unit to upgrade spy.");
            } else {
                gameContext.clickHistory.put("spy", true);
                sceneManager.switchTo("GameMain.fxml");
            }
        }
    }

    @FXML
    public void handleCancelButton() {
        sceneManager.switchTo("GameMain.fxml");
    }
}
