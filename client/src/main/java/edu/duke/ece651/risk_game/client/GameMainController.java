package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.Response;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class GameMainController extends GameController{
    @FXML
    Button submit;
    public GameMainController(){
        super();
    }

    public void initialize(){
        super.initialize();
        System.out.println("main init roomID now is " + gameContext.currentRoomID);
    }
    @FXML
    public void handleAttackButton(){
        sceneManager.switchTo("Attack.fxml");
    }

    @FXML
    public void handleMoveButton(){
        sceneManager.switchTo("Move.fxml");
    }

    @FXML
    public void handleUpgradeButton(){
        sceneManager.switchTo("Upgrade.fxml");
    }
    @FXML
    public void handleSwitchButton(){
        sceneManager.switchTo("RoomSelect.fxml");
    }
    @FXML
    public void handleCommitButton() {
        Response response;
        try {
            response = gameContext.httpClient.sendCommit(gameContext.currentRoomID, gameContext.playerIDMap.get(gameContext.currentRoomID));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        gameContext.update(response);
        if (response.isLose()) {
            submit.setDisable(true);
            sceneManager.switchTo("Defeat.fxml");
        } else if (response.isEnd()) {
            sceneManager.switchTo("Victory.fxml");
        } else {
            sceneManager.switchTo("GameMain.fxml");
        }
    }
}