package edu.duke.ece651.risk_game.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private Stage stage;

    private SceneManager() {}

    private static final class InstanceHolder {
        private static final SceneManager instance = new SceneManager();
    }

    public static SceneManager getInstance() {
        return InstanceHolder.instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void switchTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage createNewWindow(String fxmlFile) {
        Stage newStage = new Stage();
        newStage.setResizable(false);
        //newStage.initStyle(StageStyle.UNDECORATED);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
            return newStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newStage;
    }


}