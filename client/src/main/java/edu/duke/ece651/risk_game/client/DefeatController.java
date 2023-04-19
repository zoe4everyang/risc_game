package edu.duke.ece651.risk_game.client;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class DefeatController extends UIController{
    @FXML
    public void handleContinueButton() {
        sceneManager.switchTo("RoomSelect.fxml");
    }

    @FXML
    public void handleExitButton() {
        Platform.exit();
    }
}
