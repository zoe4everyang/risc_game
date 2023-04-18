package edu.duke.ece651.risk_game.client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is used to start the game.
 * It is used by the RISCFront class.
 */
public class RISCFront extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.getInstance().setStage(primaryStage);
        SceneManager.getInstance().switchTo("login.fxml");
    }

    @FXML
    public static void main(String[] args) throws IOException {
        Application.launch(args);
    }




}

