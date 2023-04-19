package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionStatus;
import javafx.fxml.FXML;

import java.io.IOException;

public class UpgradeTechController extends GameController{
    public void initialize() {
        super.initialize();
    }

    @FXML
    public void handleYesButton() {
        ActionStatus status;
        try {
            status = gameContext.httpClient.sendUpgradeTech(gameContext.currentRoomID, gameContext.playerIDMap.get(gameContext.currentRoomID), true);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (!status.isSuccess()) {
            throw new IllegalArgumentException(status.getErrorMessage());
        } else {
            sceneManager.switchTo("Game.fxml");
        }
    }

    @FXML
    public void handleNoButton() {
        sceneManager.switchTo("GameMain.fxml");
    }
}
