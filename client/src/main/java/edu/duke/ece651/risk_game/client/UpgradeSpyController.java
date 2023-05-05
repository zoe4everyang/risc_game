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
        clickList[gameContext.finalClickedTerritoryID].run();
    }

    @FXML
    public void handleUpgradeButton() throws IOException {
        ActionStatus status = gameContext.httpClient.sendUpgradeSpy(gameContext.currentRoomID, gameContext.playerIDMap.get(gameContext.currentRoomID),
                gameContext.finalClickedTerritoryID, Integer.parseInt(unitLevelComboBox.getValue().substring(6)));
        if (!status.isSuccess()) {
            gameContext.showErrorAlert("Error", "You don't have enough tech level or unit to upgrade spy.");
        } else {
            sceneManager.switchTo("GameMain.fxml");
        }
    }

    @FXML
    public void handleCancelButton() {
        sceneManager.switchTo("GameMain.fxml");
    }
}
