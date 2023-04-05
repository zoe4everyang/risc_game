package edu.duke.ece651.risk_game.client;

public interface Viewer {
    String display();
    void displayGamePrompt();
    void displayPlacementPrompt();
    void displayStartPrompt();
}
