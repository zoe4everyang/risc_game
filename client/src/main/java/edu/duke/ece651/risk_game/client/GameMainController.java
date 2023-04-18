package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.Response;
import javafx.fxml.FXML;

import java.io.IOException;

public class GameMainController extends GameController{
    public GameMainController(){
        super();
    }

    public void initialize(){
        super.initialize();
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
    public void handleCommitButton() {
        Response response;
        try {
            response = gameContext.httpClient.sendCommit(gameContext.currentRoomID);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        gameContext.update(response);
        if (response.isEnd()) {
            sceneManager.switchTo("Result.fxml");
        } else {
            sceneManager.switchTo("Game.fxml");
        }
    }
}