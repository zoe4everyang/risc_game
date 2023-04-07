package edu.duke.ece651.risk_game.client;
import edu.duke.ece651.risk_game.shared.Message;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class InputController {
    private RISCClient httpClient;
    private InputStream input;
    private ClientChecker checker;
    private Viewer riskViewer;
    private Message inputMessage;

    public InputController(InputStream input, Viewer riskViewer) {
        this.httpClient = new RISCClient();
        this.input = input;
        this.riskViewer = riskViewer;
    }

    // 不知道下面这两个函数都是干啥的
    public void startGame() {
        try {
            InitResponse initResponse = httpClient.sendStart();
            riskViewer.update(initResponse.getTerritories());
        } catch (IOException e) {
            System.out.println("Error while sending start request: " + e.getMessage());
        }
    }

    private void initPhase() {
        List<Integer> unitPlacement = checker.getUnitPlacement(input);
        PlacementRequest placementRequest = new PlacementRequest(checker.getPlayerId(), unitPlacement);
        try {
            Response response = httpClient.sendPlacement(placementRequest);
            riskViewer.update(response.getTerritories());
        } catch (IOException e) {
            System.out.println("Error while sending placement request: " + e.getMessage());
        }
    }

    
    private void placementPhase() {
        // TODO: TextView(ArrayList<Territory> toDisplay,int game_end,int fail_the_game, int unit_count, int player_id, String player_name) 
        // INPUT THE 
        TextView TextViewer = TextView(toDisplay, 0, 0, 100, player_id, player_name);
        ArrayList<Integer> Placement = TextViewer.getPlacement();
        // TODO: return the PlacementRequest to the seriver
    }

    private void gamePhase() {
        // TODO: input the require information
        // TextView(ArrayList<Territory> toDisplay,int game_end,int fail_the_game, int unit_count, int player_id, String player_name)
        TextView TextViewer = TextView(toDisplay, 0, 0, 100, player_id, player_name);
        TextViewer.playOneTurn();
        ArrayList<Integer> Placement = TextViewer.getPlacement();
        ArrayList<Integer> MoveFrom = TextViewer.getMoveFrom();
        ArrayList<Integer> MoveTo = TextViewer.getMoveTo();
        ArrayList<Integer> MoveNums = TextViewer.getMoveNums();
        ArrayList<Integer> AttackFrom = TextViewer.getAttackFrom();
        ArrayList<Integer> AttackTo = TextViewer.getAttackTo();
        ArrayList<Integer> AttackNums = TextViewer.getAttackNums();
        ArrayList<Integer> DefenseFrom = TextViewer.getDefenseFrom();
        ArrayList<Integer> DefenseTo = TextViewer.getDefenseTo();
        ArrayList<Integer> DefenseNums = TextViewer.getDefenseNums();
        // TODO: return the informationRequest to the seriver
    }

    public void run() {
        startGame();
        initPhase();
        placementPhase();
        gamePhase();
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