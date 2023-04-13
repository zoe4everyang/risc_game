package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.InitResponse;
import edu.duke.ece651.risk_game.shared.Response;

import java.util.HashMap;

/**
 * This interface is used to display the game to the user.
 * It is used by the InputController class.
 * It is implemented by the TextView class.
 * It is used by the RISCFront class.
 */
public interface Viewer {

    public void initPrompt();
    public void placePrompt(InitResponse initResponse, HashMap<Integer, String> territoryNameMap);
    public void placeOneTerritoryPrompt(String territoryName);
    public void losePrompt();
    //public void resultPrompt(boolean failTheGame, Integer winner);
    public void resultPrompt(boolean failTheGame);
    public void displayTheWorld(Response response, HashMap<Integer, String> territoryNameMap);
}
