package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionStatus;
import edu.duke.ece651.risk_game.shared.UpgradeUnitRequest;
import javafx.fxml.FXML;

import java.io.IOException;

public class UpgradeUnitController extends GameController{
    public void initialize() {

    }

    @FXML
    public void handleUpgradeButton() {
        // TODO: get the input from the text field
        String[] command = {"DKU", "1", "2"};
        int territory = gameContext.territoryIDMaps.get(gameContext.currentRoomID).get(command[0]);
        int unit = Integer.parseInt(command[1]);
        int level = Integer.parseInt(command[2]);
        UpgradeUnitRequest request = new UpgradeUnitRequest(gameContext.playerIDMap.get(gameContext.currentRoomID), territory, unit, level);
        ActionStatus status;
        try {
            status = gameContext.httpClient.sendUpgradeUnit(gameContext.currentRoomID, request);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Please input again!");
            return;
        }
        if (!status.isSuccess()) {
            throw new IllegalArgumentException(status.getErrorMessage());
        } else {
            sceneManager.switchTo("Game.fxml");
        }
    }
}
