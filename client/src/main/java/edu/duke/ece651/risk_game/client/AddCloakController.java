package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionStatus;
import javafx.fxml.FXML;

import java.io.IOException;

public class AddCloakController extends GameController{

        public void initialize() {
            super.initialize();
            clickList[gameContext.finalClickedTerritoryID].run();
        }

        @FXML
        public void handleYesButton() throws IOException {
            ActionStatus status = gameContext.httpClient.sendAddCloak(gameContext.currentRoomID,
                    gameContext.playerIDMap.get(gameContext.currentRoomID), gameContext.finalClickedTerritoryID);
            if (!status.isSuccess()) {
                gameContext.showErrorAlert("Error", "You don't have enough tech points to add cloak.");
            }
            sceneManager.switchTo("GameMain.fxml");
        }

        @FXML
        public void handleNoButton() {
            sceneManager.switchTo("GameMain.fxml");
        }
}
