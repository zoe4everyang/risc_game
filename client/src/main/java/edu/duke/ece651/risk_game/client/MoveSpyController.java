package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionStatus;
import edu.duke.ece651.risk_game.shared.Territory;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class MoveSpyController extends GameController{
    @FXML
    ComboBox<String> toComboBox;

    public void initialize() {
        super.initialize();
        for (Territory territory : gameContext.territories) {
            toComboBox.getItems().add(territory.getName());
        }
        clickList[gameContext.finalClickedTerritoryID].run();
    }

    @FXML
    public void handleMoveSpyButton() throws IOException {
        int to = gameContext.territoryIDMaps.get(gameContext.currentRoomID).get(toComboBox.getValue());
        ActionStatus status = gameContext.httpClient.sendMoveSpy(gameContext.currentRoomID,
                                gameContext.playerIDMap.get(gameContext.currentRoomID), to);
        if (!status.isSuccess()) {
            gameContext.showErrorAlert("Error", "You could only move 1 territory at a time in enemy territory.");
        } else {
            sceneManager.switchTo("GameMain.fxml");
        }
    }

    @FXML
    public void handleCancelButton() {
        sceneManager.switchTo("GameMain.fxml");
    }
}
