package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionStatus;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class LoginController extends UIController {

    @FXML
    private TextArea usernameField;
    @FXML
    private PasswordField passwordField;

    public LoginController() {
        super();
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
