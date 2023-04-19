package edu.duke.ece651.risk_game.client;

import javafx.fxml.FXML;

public class UpgradeController extends GameController{
    public void initialize() {
        super.initialize();
    }

    @FXML
    public void handleTechButton() {
        sceneManager.switchTo("UpgradeTech.fxml");
    }

    @FXML
    public void handleUnitButton() {
        sceneManager.switchTo("UpgradeUnit.fxml");
    }
}
