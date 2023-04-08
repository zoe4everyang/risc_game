package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.InitResponse;
import edu.duke.ece651.risk_game.shared.Response;

import java.util.HashMap;

public interface Viewer {
//    void display(Response response);
//    void displayGamePrompt();
//    String readChoose();
//    int playGamePrompt() throws IOException;
//    void playOneTurn() throws IOException;
//    public ArrayList<Integer> getMoveFrom();
//    public ArrayList<Integer> getMoveTo();
//    public ArrayList<Integer> getMoveNums();
//    public ArrayList<Integer> getAttackFrom();
//    public ArrayList<Integer> getAttackTo();
//    public ArrayList<Integer> getAttackNums();
//
//    void initPrompt(InitResponse response);
    public void initPrompt();
    public void placePrompt(InitResponse initResponse, HashMap<Integer, String> territoryNameMap);
    public void placeOneTerritoryPrompt(String territoryName);
    public void losePrompt();
    //public void resultPrompt(boolean failTheGame, Integer winner);
    public void resultPrompt(boolean failTheGame);
    public void displayTheWorld(Response response, HashMap<Integer, String> territoryNameMap);

}
