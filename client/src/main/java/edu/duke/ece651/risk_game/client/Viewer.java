package edu.duke.ece651.risk_game.client;

import java.io.IOException;
import java.util.ArrayList;

public interface Viewer {
    String display();
    void displayGamePrompt();
    String readChoose();
    int playGamePrompt() throws IOException;
    void playOneTurn() throws IOException;
    public ArrayList<Integer> getMoveFrom();
    public ArrayList<Integer> getMoveTo();
    public ArrayList<Integer> getMoveNums();
    public ArrayList<Integer> getAttackFrom();
    public ArrayList<Integer> getAttackTo();
    public ArrayList<Integer> getAttackNums();
}
