package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionRequest;
import edu.duke.ece651.risk_game.shared.ActionStatus;
import edu.duke.ece651.risk_game.shared.Territory;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;

public class MoveController extends GameController{
    @FXML
    ComboBox<String> fromComboBox;
    @FXML
    ComboBox<String> toComboBox;
    @FXML
    ComboBox<String> unitLevelComboBox;
    @FXML
    TextField unitNumTextField;

    public void initialize() {
        super.initialize();
        for (Territory territory : gameContext.territories) {
            if (territory.getOwner() == gameContext.playerIDMap.get(gameContext.currentRoomID)) {
                fromComboBox.getItems().add(territory.getName());
                toComboBox.getItems().add(territory.getName());
            }
        }
        for (int i = 1; i <= 6; i++) {
            unitLevelComboBox.getItems().add("Level " + i);
        }
        unitNumTextField.setTextFormatter(integerFormatter);
    }

    @FXML
    public void handleMoveButton() {
        int from = Integer.parseInt(fromComboBox.getValue());
        int to = Integer.parseInt(toComboBox.getValue());
        int unitNum = Integer.parseInt(unitNumTextField.getText());
        int unitLevel = Integer.parseInt(unitLevelComboBox.getValue());
        ArrayList<Integer> units = new ArrayList<>(Collections.nCopies(6,0));
        units.set(unitLevel - 1, unitNum);
        ActionRequest request = new ActionRequest(gameContext.playerIDMap.get(gameContext.currentRoomID), from, to, units);
        ActionStatus status = gameContext.httpClient.sendMove(gameContext.currentRoomID, request);
        if (!status.isSuccess()) {
            gameContext.showErrorAlert("Your Move command is invalid.",status.getErrorMessage());
        } else {
            sceneManager.switchTo("GameMain.fxml");
        }
    }
}
