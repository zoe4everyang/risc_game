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
        gameContext.finalClickedTerritoryID = -1;
        super.initialize();
        //gameContext.showInfoAlert("Welcome to the game!", "Please click on the map to choose the start territory or territory to operate on.");
    }

    @FXML
    public void handleAttackButton(){
        //gameContext.finalClickedTerritoryID = clickOnTerritoryID;
        if (gameContext.finalClickedTerritoryID == -1) {
            gameContext.showErrorAlert("Error", "Please click on the map to choose your start territory.");
        } else if (gameContext.territories.get(gameContext.finalClickedTerritoryID).getOwner() != gameContext.playerIDMap.get(gameContext.currentRoomID)) {
            gameContext.showErrorAlert("Error", "You could only attack from your own territory.");
        } else {
            sceneManager.createNewWindow("Attack.fxml");
        }
    }

    @FXML
    public void handleMoveButton(){
        //gameContext.finalClickedTerritoryID = clickOnTerritoryID;
        if (gameContext.finalClickedTerritoryID == -1) {
            gameContext.showErrorAlert("Error", "Please click on the map to choose your start territory.");
        } else if (gameContext.territories.get(gameContext.finalClickedTerritoryID).getOwner() != gameContext.playerIDMap.get(gameContext.currentRoomID)) {
            gameContext.showErrorAlert("Error", "You could only move from your own territory.");
        } else {
            sceneManager.createNewWindow("Move.fxml");
        }
    }

    @FXML
    public void handleUpgradeTechButton(){
        if (gameContext.clickHistory.get("upgradeTech")) {
            gameContext.showErrorAlert("Error", "You could only upgrade tech once per turn.");
        } else {
            sceneManager.switchTo("UpgradeTech.fxml");
        }
    }

    @FXML
    public void handleUpgradeUnitButton(){
        sceneManager.switchTo("UpgradeUnit.fxml");
    }

    @FXML
    public void handleSpyButton() {
        if (gameContext.clickHistory.get("spy")) {
            gameContext.showErrorAlert("Error", "You could only upgrade or move your spy once per turn.");
        } else if (gameContext.playerInfo.getHasSpy()) {
            sceneManager.switchTo("MoveSpy.fxml");
        } else {
            sceneManager.switchTo("UpgradeSpy.fxml");
        }
    }

    @FXML
    public void handleCloakButton() {
        if (!gameContext.playerInfo.getCanCloak()) {
            if (gameContext.clickHistory.get("cloak")) {
                gameContext.showErrorAlert("Error", "You could only upgrade cloak once.");
            } else {
                sceneManager.switchTo("UpgradeCloak.fxml");
            }
        } else {
            sceneManager.switchTo("AddCloak.fxml");
        }
    }

    @FXML
    public void handleCommitButton() {
        Response response;
        //Stage loadingStage = sceneManager.createNewWindow("Loading.fxml");
        try {
            response = gameContext.httpClient.sendCommit(gameContext.currentRoomID, gameContext.playerIDMap.get(gameContext.currentRoomID));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //loadingStage.close();
        if (response.isLose()) {
            submit.setDisable(true);
            sceneManager.switchTo("Defeat.fxml");
        } else if (response.isEnd()) {
            sceneManager.switchTo("Victory.fxml");
        } else {
            gameContext.update(response);
            gameContext.clickHistory.replaceAll((k, v) -> false);
            sceneManager.switchTo("GameMain.fxml");
        }
    }

    @FXML
    public void handleSwitchButton(){
        sceneManager.switchTo("RoomSelect.fxml");
    }
}