package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.Response;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMainController extends GameController{

    @FXML
    Button submit;
    public GameMainController(){
        super();
    }

    public void initialize(){
        super.initialize();
        gameContext.showInfoAlert("Welcome to the game!", "Please click on the map to choose the start territory or territory to operate on.");
    }

    @FXML
    public void handleAttackButton(){
        gameContext.finalClickedTerritoryID = clickOnTerritoryID;
        if (gameContext.finalClickedTerritoryID == -1) {
            gameContext.showErrorAlert("Error", "Please click on the map to choose your start territory.");
        } else {
            sceneManager.createNewWindow("Attack.fxml");
        }
    }

    @FXML
    public void handleMoveButton(){
        gameContext.finalClickedTerritoryID = clickOnTerritoryID;
        if (gameContext.finalClickedTerritoryID == -1) {
            gameContext.showErrorAlert("Error", "Please click on the map to choose your start territory.");
        } else {
            sceneManager.createNewWindow("Move.fxml");
        }
    }

    @FXML
    public void handleUpgradeTechButton(){
        sceneManager.switchTo("UpgradeTech.fxml");
    }

    @FXML
    public void handleUpgradeUnitButton(){
        gameContext.finalClickedTerritoryID = clickOnTerritoryID;
        if (gameContext.finalClickedTerritoryID == -1) {
            gameContext.showErrorAlert("Error", "Please click on the map to choose the territory to operate on.");
        } else {
            sceneManager.switchTo("UpgradeUnit.fxml");
        }
    }

    @FXML
    public void handleSpyButton() {
        if (gameContext.playerInfo.getHasSpy()) {
            sceneManager.switchTo("MoveSpy.fxml");
        } else {
            gameContext.finalClickedTerritoryID = clickOnTerritoryID;
            if (gameContext.finalClickedTerritoryID == -1) {
                gameContext.showErrorAlert("Error", "Please click on the map to choose the territory to operate on.");
            } else {
                sceneManager.switchTo("UpgradeSpy.fxml");
            }
        }
    }

    @FXML
    public void handleCloakButton() {
        if (!gameContext.playerInfo.getCanCloak()) {
            sceneManager.switchTo("UpgradeCloak.fxml");
        } else {
            gameContext.finalClickedTerritoryID = clickOnTerritoryID;
            if (gameContext.finalClickedTerritoryID == -1) {
                gameContext.showErrorAlert("Error", "Please click on the map to choose the territory to operate on.");
            } else{
                sceneManager.switchTo("AddCloak.fxml");
            }
        }
    }

    @FXML
    public void handleCommitButton() {
        Response response;
        Stage loadingStage = sceneManager.createNewWindow("Loading.fxml");
        try {
            response = gameContext.httpClient.sendCommit(gameContext.currentRoomID, gameContext.playerIDMap.get(gameContext.currentRoomID));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        loadingStage.close();
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

    @FXML
    public void handleSwitchButton(){
        sceneManager.switchTo("RoomSelect.fxml");
    }
}