package edu.duke.ece651.risk_game.client;

import javafx.application.Platform;
import javafx.fxml.FXML;

import java.io.IOException;

public class VictoryController extends UIController{
    @FXML
    public void handleContinueButton() {
        sceneManager.switchTo("RoomSelect.fxml");
    }

    @FXML
    public void handleExitButton() throws IOException {
        Platform.exit();
    }
}
