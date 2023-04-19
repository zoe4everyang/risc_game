package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionStatus;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends UIController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public LoginController() {
        super();
    }

    public void initialize() {
        gameContext.showInfoAlert("Welcome to RISC!", "Please enter your username and password to login.\n" +
                "It will automatically create a new account if you haven't registered before.");
    }
    @FXML
    public void handleLoginButton() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        ActionStatus status = gameContext.httpClient.sendLogin(username, password);
        if (status.isSuccess()) {
            gameContext.username = username;
            sceneManager.switchTo("RoomSelect.fxml");
        } else {
            gameContext.showErrorAlert("Login Failed", status.getErrorMessage());
        }
    }
}
