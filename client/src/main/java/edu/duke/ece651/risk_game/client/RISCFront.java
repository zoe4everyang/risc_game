package edu.duke.ece651.risk_game.client;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * This class is used to start the game.
 * It is used by the RISCFront class.
 */
public class RISCFront extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        SceneManager.getInstance().setStage(primaryStage);
        SceneManager.getInstance().switchTo("Login.fxml");
    }

    public static void main(String[] args) throws IOException {
        Application.launch();
    }

}

