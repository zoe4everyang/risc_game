package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionStatus;
import javafx.fxml.FXML;

import java.io.IOException;

public class UpgradeCloakController extends GameController{

        public void initialize() {
            super.initialize();
        }

        @FXML
        public void handleYesButton() throws IOException {
            ActionStatus status = gameContext.httpClient.sendUpgradeCloak(gameContext.currentRoomID,
                                    gameContext.playerIDMap.get(gameContext.currentRoomID));
            if (!status.isSuccess()) {
                gameContext.showErrorAlert("Error", "You don't have enough tech level to upgrade cloak.");
            } else {
                gameContext.clickHistory.put("cloak", true);
            }
            sceneManager.switchTo("GameMain.fxml");
        }

        @FXML
        public void handleNoButton() {
            sceneManager.switchTo("GameMain.fxml");
        }
}
