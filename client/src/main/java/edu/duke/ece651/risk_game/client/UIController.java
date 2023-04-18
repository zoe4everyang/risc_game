package edu.duke.ece651.risk_game.client;

public abstract class UIController {
    protected final SceneManager sceneManager;
    protected final GameContext gameContext;
    public UIController() {
        sceneManager = SceneManager.getInstance();
        gameContext = GameContext.getInstance();
    }
}
