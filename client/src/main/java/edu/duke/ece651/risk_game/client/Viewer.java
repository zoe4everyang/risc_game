package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.Response;

import java.util.HashMap;
import java.util.Set;

/**
 * This interface is used to display the game to the user.
 * It is used by the InputController class.
 * It is implemented by the TextView class.
 * It is used by the RISCFront class.
 */
public interface Viewer {

    void initPrompt();
    void usernamePrompt();
    void passwordPrompt();
    void loginFailedPrompt();
    void roomSelectPrompt(Set<Integer> roomIDs);
    void roomSelectFailedPrompt();
    void placePrompt(Response initResponse, HashMap<Integer, String> territoryNameMap);
    void placeOneTerritoryPrompt(String territoryName);
    void losePrompt();
    //public void resultPrompt(boolean failTheGame, Integer winner);
    void resultPrompt(Integer roomID, boolean failTheGame);
    void exitPrompt();
    void displayTheWorld(Response response, HashMap<Integer, String> territoryNameMap);

}
