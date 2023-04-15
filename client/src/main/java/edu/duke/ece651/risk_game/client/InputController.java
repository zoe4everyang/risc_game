package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* This class is responsible for the interaction between the user and the game.
 * It will send the user's input to the server and display the response from the server.
 * It will also display the game prompt to the user.
 */
public class InputController {
    private final RISCClient httpClient;
    private final BufferedReader input;
    private final Viewer riscViewer;
    private Integer playerID;
    private final HashMap<Integer, String> territoryNameMap;
    private final HashMap<String, Integer> territoryIDMap;

    /* Constructor for InputController
     * @param input: the input stream
     * @param output: the output stream
     */
    public InputController(BufferedReader input, PrintStream output){
        this.httpClient = new RISCClient();
        this.input = input;
        this.riscViewer = new TextView(output);
        this.playerID = -1;
        this.territoryNameMap = new HashMap<>();
        this.territoryIDMap = new HashMap<>();
    }

    /* This method is responsible for the whole game.
     * It will call the initPhase, placementPhase, and gamePhase methods.
     */
    public void startGame() throws IOException {
        loginPhase();
        roomSelectPhase();
        placementPhase(initResponse);
        actionPhase();
    }

    /* This method is responsible for the init phase.
     * It will send the start request to the server and get the response.
     * It will also initialize the territoryNameMap and territoryIDMap.
     */
    private InitResponse initPhase() {
        riscViewer.initPrompt();
        InitResponse initResponse = null;
        boolean error;
        do {
            error = false;
            try {
                initResponse = httpClient.sendStart();
            } catch (IOException e) {
                System.out.println("Error while sending start request: " + e.getMessage());
                error = true;
            }
        } while (error);
        assert initResponse != null;
        playerID = initResponse.getPlayerID();
        List<Territory> territories = initResponse.getTerritories();
        for (Territory territory : territories) {
            territoryNameMap.put(territory.getID(), territory.getName());
            territoryIDMap.put(territory.getName(), territory.getID());
        }
        return initResponse;
    }

    /* This method is responsible for the placement phase.
     * It will send the placement request to the server and get the response.
     * It will also display the game prompt to the user.
     * @param placement: the placement of the player
     * @param unitAvailable: the number of units available for the player
     * @param territoryIDs: the IDs of the territories owned by the player
     */
    private void readPlacement(ArrayList<Integer> placement, Integer unitAvailable, ArrayList<Integer> territoryIDs) throws IOException {
        int unitLeft = unitAvailable;
        for (Integer territoryID : territoryIDs) {
            riscViewer.placeOneTerritoryPrompt(territoryNameMap.get(territoryID));
            String numStr = "";
            numStr = input.readLine();
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

    /* This method is responsible for the placement phase.
     * It will send the placement request to the server and get the response.
     * It will also display the game prompt to the user.
     * @param initResponse: the response from the server
     */
    private void placementPhase(InitResponse initResponse) throws IOException {
        riscViewer.placePrompt(initResponse, territoryNameMap);
        ArrayList<Integer> territoryIDs = new ArrayList<>();
        for (Territory territory : initResponse.getTerritories()) {
            if (territory.getOwner() == playerID) {
                territoryIDs.add(territory.getID());
            }
        }
        ArrayList<Integer> placement = new ArrayList<Integer>(Collections.nCopies(territoryNameMap.size(), -1));
        boolean error;
        do {
            error = false;
            try {
                readPlacement(placement, initResponse.getUnitAvailable(), territoryIDs);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please input again!");
                error = true;
            }
        } while (error);
        PlacementRequest placementRequest = new PlacementRequest(playerID, placement);
        Response response = null;
        try {
            response = httpClient.sendPlacement(placementRequest);
        } catch (IOException e) {
            System.out.println("Error while sending placement request: " + e.getMessage());
        }
        riscViewer.displayTheWorld(response, territoryNameMap);
    }

    /* This method is responsible for the game phase.
     * It will send the game request to the server and get the response.
     * It will also display the game prompt to the user.
     */
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

    /* This method is responsible for the game phase.
     * It will send the game request to the server and get the response.
     * It will also display the game prompt to the user.
     * @param command: the command input by the user
     */
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

    /* This method is responsible for the game phase.
     * It will send the game request to the server and get the response.
     * It will also display the game prompt to the user.
     * @param MoveFrom: the list of territories to move from
     * @param MoveTo: the list of territories to move to
     * @param MoveNums: the list of units to move
     * @param AttackFrom: the list of territories to attack from
     * @param AttackTo: the list of territories to attack to
     * @param AttackNums: the list of units to attack
     */
    private void getOneTurnInput(ArrayList<Integer> MoveFrom, ArrayList<Integer> MoveTo, ArrayList<Integer> MoveNums,
                                ArrayList<Integer> AttackFrom, ArrayList<Integer> AttackTo, ArrayList<Integer> AttackNums) throws IOException {
        System.out.println("Player " + playerID + ", what would you like to do?\n"
                                + "(M)ove\n"
                                + "(A)ttack\n"
                                + "(D)one");

        while(true){
            String[] command = null;
            boolean error;
            do {
                error = false;
                try {
                    command = readCommand();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Please input again!");
                    error = true;
                }
            } while (error);
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

    /* This method is responsible for the game phase.
     * It will send the game request to the server and get the response.
     * It will also display the game prompt to the user.
     */
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
            //if (!failTheGame) {
                getOneTurnInput(MoveFrom, MoveTo, MoveNums, AttackFrom, AttackTo, AttackNums);
            //}
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
                break;
//                riscViewer.losePrompt();
            }
            riscViewer.displayTheWorld(response, territoryNameMap);
        }
        //riscViewer.resultPrompt(failTheGame, response.getTerritories().get(0).getOwner());
        if (!failTheGame) {
            try {
                httpClient.sendEnd();
            } catch (IOException e) {
                System.out.println("You win!");
            }
        }
        riscViewer.resultPrompt(failTheGame);
    }
}
