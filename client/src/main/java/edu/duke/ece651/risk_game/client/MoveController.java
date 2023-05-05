package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.Territory;
import javafx.fxml.FXML;

public class MoveController extends ActionController{
    public void initialize() {
        super.initialize();
        for (Territory territory : gameContext.territories) {
            if (territory.getOwner() == gameContext.playerIDMap.get(gameContext.currentRoomID)) {
                toComboBox.getItems().add(territory.getName());
            }
        }
    }

    @FXML
    public void handleAttackButton() {
        handleActionButton("M");
    }
}
