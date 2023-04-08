package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class InputController {
    private final RISCClient httpClient;
    private final BufferedReader input;
    private final Viewer riscViewer;
    private Integer playerID;
    private final HashMap<Integer, String> territoryNameMap;
    private final HashMap<String, Integer> territoryIDMap;

    public InputController(BufferedReader input, PrintStream output){
        this.httpClient = new RISCClient();
        this.input = input;
        this.riscViewer = new TextView(output);
        this.playerID = -1;
        this.territoryNameMap = new HashMap<>();
        this.territoryIDMap = new HashMap<>();
    }

    public void startGame() throws IOException {
        InitResponse initResponse = initPhase();
        placementPhase(initResponse);
        gamePhase();
    }

    private InitResponse initPhase() {
        riscViewer.initPrompt();
        InitResponse initResponse = null;
        try {
            initResponse = httpClient.sendStart();
        } catch (IOException e) {
            System.out.println("Error while sending start request: " + e.getMessage());
        }
        assert initResponse != null;
        playerID = initResponse.getPlayerID();
        List<Territory> territories = initResponse.getTerritories();
        for (Territory territory : territories) {
            territoryNameMap.put(territory.getID(), territory.getName());
            territoryIDMap.put(territory.getName(), territory.getID());
        }
        return initResponse;
    }

    private void readPlacement(ArrayList<Integer> placement, Integer unitAvailable, ArrayList<Integer> territoryIDs) {
        int unitLeft = unitAvailable;
        for (Integer territoryID : territoryIDs) {
            riscViewer.placeOneTerritoryPrompt(territoryNameMap.get(territoryID));
            String numStr = "";
            try {
                numStr = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int num = Integer.parseInt(numStr);
            if (num > unitLeft) {
                throw new IllegalArgumentException("You don't have enough units!");
            }
            unitLeft -= num;
            placement.set(territoryID, num);
        }
        if (unitLeft != 0) {
            throw new IllegalArgumentException("You have not placed all units!");
        }
    }

    private void placementPhase(InitResponse initResponse) {
        riscViewer.placePrompt(initResponse, territoryNameMap);
        ArrayList<Integer> territoryIDs = new ArrayList<>();
        for (Territory territory : initResponse.getTerritories()) {
            if (territory.getOwner() == playerID) {
                territoryIDs.add(territory.getID());
            }
        }
        ArrayList<Integer> placement = new ArrayList<Integer>(Collections.nCopies(territoryNameMap.size(), -1));
        readPlacement(placement, initResponse.getUnitAvailable(), territoryIDs);
        PlacementRequest placementRequest = new PlacementRequest(playerID, placement);
        Response response = null;
        try {
            response = httpClient.sendPlacement(placementRequest);
        } catch (IOException e) {
            System.out.println("Error while sending placement request: " + e.getMessage());
        }
        riscViewer.displayTheWorld(response, territoryNameMap);
    }

    private String[] readCommand() {
        String text = "";
        try {
            text = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] command = text.split(" ");
        simpleCommandCheck(command);
        return command;
    }

    private void simpleCommandCheck(String[] command) {
        // TODO: more check logic (e.g. checking the existence of territory and unit)
        if (command.length == 0) {
            throw new IllegalArgumentException("No Input read!");
        } else {
            String commandType = command[0];
            if (commandType.equals("D") && command.length != 1) {
                throw new IllegalArgumentException("You can only input 'D' to end your turn!");
            } else if (commandType.equals("M") && command.length != 4) {
                throw new IllegalArgumentException("You can only input 'M' 'from' 'to' 'num' to move!");
            } else if (commandType.equals("A") && command.length != 4) {
                throw new IllegalArgumentException("You can only input 'A' 'from' 'to' 'num' to attack!");
            } else if (!commandType.equals("A") && !commandType.equals("M") && !commandType.equals("D")) {
                throw new IllegalArgumentException("you can only input 'M' 'A' AND 'D' to play the game!");
            }
        }
    }

    private void getOneTurnInput(ArrayList<Integer> MoveFrom, ArrayList<Integer> MoveTo, ArrayList<Integer> MoveNums,
                                ArrayList<Integer> AttackFrom, ArrayList<Integer> AttackTo, ArrayList<Integer> AttackNums) throws IOException {
        System.out.println("Player " + playerID + ", what would you like to do?\n"
                                + "(M)ove\n"
                                + "(A)ttack\n"
                                + "(D)one");

        while(true){
            String[] command = readCommand();
            if (command[0].equals("D")){
                break;
            } else {
                int from = territoryIDMap.get(command[1]);
                int to = territoryIDMap.get(command[2]);
                int num = Integer.parseInt(command[3]);
                if (command[0].equals("M")){
                    MoveFrom.add(from);
                    MoveTo.add(to);
                    MoveNums.add(num);
                } else if (command[0].equals("A")){
                    AttackFrom.add(from);
                    AttackTo.add(to);
                    AttackNums.add(num);
                }
            }
        }
    }

    private void gamePhase() throws IOException {
        Response response = null;
        Boolean gameEnd = false, failTheGame = false;
        while (!gameEnd){
            ArrayList<Integer> MoveFrom = new ArrayList<>();
            ArrayList<Integer> MoveTo = new ArrayList<>();
            ArrayList<Integer> MoveNums = new ArrayList<>();
            ArrayList<Integer> AttackFrom = new ArrayList<>();
            ArrayList<Integer> AttackTo = new ArrayList<>();
            ArrayList<Integer> AttackNums = new ArrayList<>();
            if (!failTheGame) {
                getOneTurnInput(MoveFrom, MoveTo, MoveNums, AttackFrom, AttackTo, AttackNums);
            }
            ActionRequest actionRequest = new ActionRequest(playerID, MoveFrom, MoveTo, MoveNums, AttackFrom, AttackTo, AttackNums);
            try {
                response = httpClient.sendAction(actionRequest);
            } catch (IOException e) {
                System.out.println("Error while sending action request: " + e.getMessage());
            }
            assert response != null;
            gameEnd = response.isEnd();
            failTheGame = response.isLose();
            if (failTheGame) {
                riscViewer.losePrompt();
            }
            riscViewer.displayTheWorld(response, territoryNameMap);
        }
        riscViewer.resultPrompt(failTheGame, response.getTerritories().get(0).getOwner());

    }
}


// HOW to use the textviewer:

// initalize the units:
// TextView(ArrayList<Territory> toDisplay,int game_end,int fail_the_game, int unit_count, int player_id, String player_name)
// when you initialize the TextView and the unit_count is not 0, then the textviewer will enter the phase to ask player to intitalize their units
// get the distribution of the Territory by TextViewer.getDistribution
/*example:
TextView TextViewer = TextView(toDisplay, 0, 0, 100, player_id, player_name);
ArrayList<Integer> Placement = TextViewer.getPlacement();
*/


// Play the game:
// TextView(ArrayList<Territory> toDisplay,int game_end,int fail_the_game, int unit_count, int player_id, String player_name)
// initialize the TextView with the toDisplay (the territory list)
/*example:
TextView TextViewer = TextView(toDisplay, 0, 0, 0, player_id, player_name);
TextViewer.playOneTurn();
*/
// And the following function is used to get the result of playing:
/*
    public ArrayList<Integer> getMoveFrom(){
        return this.MoveFrom;
    }

    public ArrayList<Integer> getMoveTo(){
        return this.MoveTo;
    }

    public ArrayList<Integer> getMoveNums(){
        return this.MoveNums;
    }

    public ArrayList<Integer> getAttackFrom(){
        return this.AttackFrom;
    }

    public ArrayList<Integer> getAttackTo(){
        return this.AttackTo;
    }

    public ArrayList<Integer> getAttackNums(){
        return this.AttackNums;
    }
*/