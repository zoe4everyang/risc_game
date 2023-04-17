package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.BiFunction;

/* This class is responsible for the interaction between the user and the game.
 * It will send the user's input to the server and display the response from the server.
 * It will also display the game prompt to the user.
 */
public class InputController {
    private final RISCClient httpClient;
    private final BufferedReader input;
    private final Viewer riscViewer;
    private String username;
    private Integer currentRoomID;
    private final HashMap<Integer, Integer> playerIDMap;
    private final HashMap<Integer, HashMap<Integer, String>> territoryNameMaps;
    private final HashMap<Integer, HashMap<String, Integer>> territoryIDMaps;
    private final HashMap<String, BiFunction<Integer, ActionRequest, ActionStatus>> actionFns;

    /**
     * Constructor for InputController
     *
     * @param input:  the input stream
     * @param output: the output stream
     */
    public InputController(BufferedReader input, PrintStream output) {
        this.httpClient = new RISCClient();
        this.input = input;
        this.riscViewer = new TextView(output);
        this.username = "";
        this.currentRoomID = -1;
        this.playerIDMap = new HashMap<>();
        this.territoryNameMaps = new HashMap<>();
        this.territoryIDMaps = new HashMap<>();
        this.actionFns = new HashMap<>();
        initActionFns();
    }

    private void initActionFns() {
        actionFns.put("A", httpClient::sendAttack);
        actionFns.put("M", httpClient::sendMove);
    }

    /* This method is responsible for the whole game.
     * It will call the initPhase, placementPhase, and gamePhase methods.
     */
    public void startGame() throws IOException {
        loginPhase();
        while (true) {
            Response response = roomSelectPhase();
            if (response == null) {
                break;
            } else if (response.getUnitAvailable() >= 0) {
                placementPhase(response);
            }
            if (gamePhase()) {
                break;
            }
        }
    }

    /**
     * This method reads the user's account information.
     *
     * @return the username and password
     */
    private String[] readAccountInfo() throws IOException {
        riscViewer.usernamePrompt();
        String username = input.readLine();
        riscViewer.passwordPrompt();
        String password = input.readLine();
        return new String[]{username, password};
    }

    /**
     * This method is responsible for the login phase.
     * It will send the login request to the server and get the response.
     * It will also initialize the username.
     */
    private void loginPhase() throws IOException {
        riscViewer.initPrompt();
        while (true) {
            String[] accountInfo = readAccountInfo();
            ActionStatus status = httpClient.sendLogin(accountInfo[0], accountInfo[1]);
            if (status.isSuccess()) {
                this.username = accountInfo[0];
                break;
            } else {
                System.out.println(status.getErrorMessage());
            }
        }
    }

    /**
     * This method is responsible for the room select phase.
     * It will send the room select request to the server and get the response.
     * It will also initialize the playerIDMap.
     */
    public Response roomSelectPhase() throws IOException {
        Response response = null;
        Set<Integer> roomIDs = null;
        Integer roomID;
        // get room ID list
        while (true) {
            try {
                roomIDs = httpClient.getRoomList(this.username);
            } catch (IOException e) {
                System.out.println("Error while requesting room ID list: " + e.getMessage());
            }
            assert roomIDs != null;

            // room ID input
            while (true) {
                riscViewer.roomSelectPrompt(roomIDs);
                String inputContent = input.readLine();
                if (Objects.equals(inputContent, "exit")) {
                    return null;
                }
                roomID = Integer.parseInt(inputContent);
                if (roomIDs.contains(roomID)) {
                    break;
                } else {
                    riscViewer.roomSelectFailedPrompt();
                }
            }

            // join room
            try {
                response = httpClient.joinRoom(username, roomID);
            } catch (IOException e) {
                System.out.println("Error while sending join room request: " + e.getMessage());
            }
            if (response != null) {
                break;
            } else {
                riscViewer.roomSelectFailedPrompt();
            }
        }

        // update room Info
        currentRoomID = roomID;
        if (!playerIDMap.containsKey(roomID)) {
            playerIDMap.put(roomID, response.getPlayerInfo().getPlayerID());
        }
        if (!territoryNameMaps.containsKey(roomID)) {
            HashMap<Integer, String> territoryNameMap = new HashMap<>();
            HashMap<String, Integer> territoryIDMap = new HashMap<>();
            List<Territory> territories = response.getTerritories();
            for (Territory territory : territories) {
                territoryNameMap.put(territory.getID(), territory.getName());
                territoryIDMap.put(territory.getName(), territory.getID());
            }
            territoryNameMaps.put(roomID, territoryNameMap);
            territoryIDMaps.put(roomID, territoryIDMap);
        }

        // display the world
        riscViewer.displayTheWorld(response, territoryNameMaps.get(currentRoomID));

        return response;
    }

    /**
     * This method is responsible for the placement phase.
     * It will send the placement request to the server and get the response.
     * It will also display the game prompt to the user.
     *
     * @param placement:     the placement of the player
     * @param unitAvailable: the number of units available for the player
     * @param territoryIDs:  the IDs of the territories owned by the player
     */
    private void readPlacement(ArrayList<Integer> placement, Integer unitAvailable,
                               ArrayList<Integer> territoryIDs) throws IOException {
        int unitLeft = unitAvailable;
        for (Integer territoryID : territoryIDs) {
            riscViewer.placeOneTerritoryPrompt(territoryNameMaps.get(currentRoomID).get(territoryID));
            int num = Integer.parseInt(input.readLine());
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

    /**
     * This method is responsible for the placement phase.
     * It will send the placement request to the server and get the response.
     * It will also display the game prompt to the user.
     *
     * @param initResponse: the response from the server
     */
    private void placementPhase(Response initResponse) throws IOException {
        HashMap<Integer, String> territoryNameMap = territoryNameMaps.get(currentRoomID);
        Integer playerID = playerIDMap.get(currentRoomID);

        riscViewer.placePrompt(initResponse, territoryNameMap);
        ArrayList<Integer> territoryIDs = new ArrayList<>();
        for (Territory territory : initResponse.getTerritories()) {
            if (territory.getOwner() == playerID) {
                territoryIDs.add(territory.getID());
            }
        }
        ArrayList<Integer> placement = new ArrayList<>(Collections.nCopies(territoryNameMap.size(), -1));
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
            response = httpClient.sendPlacement(currentRoomID, placementRequest);
        } catch (IOException e) {
            System.out.println("Error while sending placement request: " + e.getMessage());
        }
        assert response != null;
        riscViewer.displayTheWorld(response, territoryNameMap);
    }

    /* This method is responsible for the game phase.
     * It will send the game request to the server and get the response.
     * It will also display the game prompt to the user.
     */
    private String[] readCommand() throws IllegalArgumentException {
        String text = "";
        try {
            text = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.split(" ");
    }

    private Boolean checkExit() throws IOException {
        riscViewer.exitPrompt();
        String answer = input.readLine();
        if (answer.equals("Y") || answer.equals("y") || answer.equals("Yes") || answer.equals("yes")) {
            return true;
        } else if (answer.equals("N") || answer.equals("n") || answer.equals("No") || answer.equals("no")) {
            return false;
        } else {
            System.out.println("Please input Y or N!");
            return checkExit();
        }
    }

    /* This method is responsible for the game phase.
     * It will send the game request to the server and get the response.
     * It will also display the game prompt to the user.
     */
    private Boolean gamePhase() throws IOException {
        Response response;
        Boolean gameEnd = false, failTheGame = false;
        while (!gameEnd && !failTheGame) {
            if (!upgradeTechPhase()) {
                return false;
            }
            if (!upgradeUnitPhase()) {
                return false;
            }
            if (actionPhase("M")) {
                return false;
            }
            if (actionPhase("A")) {
                return false;
            }
            response = httpClient.sendCommit(currentRoomID);
            gameEnd = response.isEnd();
            failTheGame = response.isLose();
            riscViewer.displayTheWorld(response, territoryNameMaps.get(currentRoomID));
        }
        riscViewer.resultPrompt(currentRoomID, failTheGame);
        return checkExit();
    }

    private boolean upgradeTechPhase() {
        String[] command;
        boolean error;
        do {
            error = false;
            try {
                command = readCommand();
                String commandType = command[0];
                if (Objects.equals(commandType, "S") && command.length == 1) {
                    return false;
                } else if (Objects.equals(commandType, "UT") && command.length == 2) {
                    if (!Objects.equals(command[1], "y") && !Objects.equals(command[1], "n")) {
                        throw new IllegalArgumentException("You can only input 'y' or 'n' to upgrade tech!");
                    }
                    ActionStatus status;
                    if (Objects.equals(command[1], "y")) {
                        status = httpClient.sendUpgradeTech(currentRoomID, playerIDMap.get(currentRoomID), true);
                    } else {
                        status = httpClient.sendUpgradeTech(currentRoomID, playerIDMap.get(currentRoomID), false);
                    }
                    if (!status.isSuccess()) {
                        throw new IllegalArgumentException(status.getErrorMessage());
                    }
                } else {
                    throw new IllegalArgumentException("You can only input 'UT' 'y/n' to decide whether upgrade tech!");
                }
            } catch (IllegalArgumentException | IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Please input again!");
                error = true;
            }
        } while (error);
        return false;
    }

    private boolean upgradeUnitPhase() {
        String[] command;
        while (true) {
            boolean error;
            do {
                error = false;
                try {
                    command = readCommand();
                    String commandType = command[0];
                    if (Objects.equals(commandType, "S") && command.length == 1) {
                        return false;
                    } else if (Objects.equals(commandType, "D") && command.length == 1) {
                        return true;
                    } else if (Objects.equals(commandType, "UU") && command.length == 4) {
                        int territory = territoryIDMaps.get(currentRoomID).get(command[1]);
                        int unit = Integer.parseInt(command[2]);
                        int level = Integer.parseInt(command[3]);
                        UpgradeUnitRequest request = new UpgradeUnitRequest(playerIDMap.get(currentRoomID), territory, unit, level);
                        ActionStatus status = httpClient.sendUpgradeUnit(currentRoomID, request);
                        if (!status.isSuccess()) {
                            throw new IllegalArgumentException(status.getErrorMessage());
                        }
                    } else {
                        throw new IllegalArgumentException("You can only input 'UU' 'territory' 'unit' 'level' to upgrade unit!");
                    }
                } catch (IllegalArgumentException | IOException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Please input again!");
                    error = true;
                }
            } while (error);
        }
    }

    private boolean actionPhase(String type) {
        String[] command;
        while (true) {
            boolean error;
            do {
                error = false;
                try {
                    command = readCommand();
                    String commandType = command[0];
                    if (Objects.equals(commandType, "S") && command.length == 1) {
                        return true;
                    } else if (Objects.equals(commandType, "D") && command.length == 1) {
                        return false;
                    } else if (Objects.equals(commandType, type) && command.length == 4) {
                        int from = territoryIDMaps.get(currentRoomID).get(command[1]);
                        int to = territoryIDMaps.get(currentRoomID).get(command[2]);
                        ArrayList<Integer> unitIDs = new ArrayList<>();
                        for (int i = 3; i < command.length; i++) {
                            unitIDs.add(Integer.parseInt(command[i]));
                        }
                        ActionRequest request = new ActionRequest(playerIDMap.get(currentRoomID), from, to, unitIDs);
                        ActionStatus status = actionFns.get(type).apply(currentRoomID, request);
                        if (!status.isSuccess()) {
                            throw new IllegalArgumentException(status.getErrorMessage());
                        }
                    } else {
                        throw new IllegalArgumentException("You can only input 'M' 'from' 'to' and units info to move!");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Please input again!");
                    error = true;
                }
            } while (error);
        }
    }
}
