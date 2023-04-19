package edu.duke.ece651.risk_game.client;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public abstract class UIController {
    protected final SceneManager sceneManager;
    protected final GameContext gameContext;
    protected TextFormatter<String> integerFormatter;
    public UIController() {
        this.sceneManager = SceneManager.getInstance();
        this.gameContext = GameContext.getInstance();
        Pattern pattern = Pattern.compile("\\d*");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (pattern.matcher(text).matches()) {
                return change;
            }
            return null;
        };
        this.integerFormatter = new TextFormatter<>(filter);
    }
}
