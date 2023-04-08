package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.InitResponse;
import edu.duke.ece651.risk_game.shared.Response;
import edu.duke.ece651.risk_game.shared.Territory;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to display the game to the user.
 * It is used by the InputController class.
 * It implements the Viewer interface.
 * It is used by the RISCFront class.
 */
public class TextView implements Viewer {
    PrintStream out;

    /**
     * This is the constructor of the TextView class.
     * @param out the PrintStream object used to print the game to the user.
     */
    public TextView(PrintStream out) {
        this.out = out;
    }
    /**
     * This method is used to display the game to the user.
     */
    @Override
    public void initPrompt() {
        out.println("Welcome to RISC Game!");
    }

    /**
     * This method is used to display the game to the user.
     * @param initResponse the InitResponse object used to display the game to the user.
     * @param territoryNameMap the HashMap object used to display the game to the user.
     */
    @Override
    public void placePrompt(InitResponse initResponse, HashMap<Integer, String> territoryNameMap) {
        Integer id = initResponse.getPlayerID();
        StringBuilder territoryNames = new StringBuilder();
        for (Territory territory : initResponse.getTerritories()) {
            if (territory.getOwner() == id) {
                territoryNames.append(territoryNameMap.get(territory.getID())).append(" ");
            }
        }
        out.println("You have been connected to the server successfully!\n" +
                "You are Player" + id + ".\n" +
                "Your territories are: \n" +
                territoryNames.toString() + "\n" +
                "You have " + initResponse.getUnitAvailable() + " units available.\n" +
                "Please place your units on the map.");
    }

    /**
     * This method is used to display the game to the user.
     * @param territoryName the String object used to display the game to the user.
     */
    @Override
    public void placeOneTerritoryPrompt(String territoryName) {
        out.println("Type in the number of units you want to place on " + territoryName + ":");
    }

    /**
     * This method is used to display the game to the user.
     */
    @Override
    public void losePrompt() {
        out.println("You lose!");
    }

    /**
     * This method is used to display the game to the user.
     * @param failTheGame the boolean object used to display the game to the user.
     */
    @Override
    public void resultPrompt(boolean failTheGame){ //, Integer winner) {
        if (failTheGame) {
            //out.println("Winner is Player" + winner + ".");
            out.println("You lose!");
        } else {
            out.println("You win!");
        }
    }

    /**
     * This method is used to display the game to the user.
     * @param response the Response object used to display the game to the user.
     * @param territoryNameMap the HashMap object used to display the game to the user.
     */
    @Override
    public void displayTheWorld(Response response, HashMap<Integer, String> territoryNameMap) {
        HashMap<Integer, ArrayList<Territory>> territory_Map = new HashMap<>();
        for (Territory territory : response.getTerritories()) {
            if (territory_Map.containsKey(territory.getOwner())) {
                territory_Map.get(territory.getOwner()).add(territory);
            } else {
                ArrayList<Territory> temp = new ArrayList<>();
                temp.add(territory);
                territory_Map.put(territory.getOwner(), temp);
            }
        }
        StringBuilder s = new StringBuilder();
        for (Integer playerID : territory_Map.keySet()) {
            s.append("Player").append(playerID).append("\n");
            s.append("-".repeat(10)).append("\n");
            for (Territory t : territory_Map.get(playerID)) {
                s.append(printInfo(t, territoryNameMap)).append("\n");
            }
        }
        System.out.println(s.toString());
    }

    /**
     * This method is used to display the game to the user.
     * @param t the Territory object used to display the game to the user.
     * @param territoryNameMap the HashMap object used to display the game to the user.
     * @return the String object used to display the game to the user.
     */
    public String printInfo(Territory t, HashMap<Integer, String> territoryNameMap) {
        StringBuilder s = new StringBuilder();
        List<Integer> distances = t.getDistances();
        s.append(t.getUnits()).append(" units in ").append(t.getName()).append(" (next to:");
        for (int i = 0; i < distances.size(); i++) {
            if (distances.get(i) == 1) {
                s.append(" ").append(territoryNameMap.get(i));
            }
        }
        s.append(")");
        return s.toString();
    }
}
