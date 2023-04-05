package edu.duke.ece651.risk_game.shared;

import java.util.List;

/**
 * This class is used to create a message that contains the initialization information
 * of response from the server.
 */
public class InitResponse extends Response {
    private final Integer unitAvailable;

    /**
     * This constructor is used to create a message that contains the information of the player's response.
     *
     * @param playerID      the ID of the player
     * @param playerName    the name of the player
     * @param territories   the list of territories that the player owns
     * @param unitAvailable the number of units that the player can place on the territories
     */
//    public InitResponse(Integer playerID, String playerName, Integer gameState, List<Territory> territories, Boolean gameEnd, Integer unitAvailable) {
//        super(playerID, playerName, gameState, territories, gameEnd);
    public InitResponse(Integer playerID, List<Territory> territories, Boolean winState, Boolean endState, Integer unitAvailable) {
        super(playerID, territories, winState, endState);
        this.unitAvailable = unitAvailable;
    }

    // This method is used to get the number of units that the player can place on the territories.
    public Integer getUnitAvailable() {
        return unitAvailable;
    }
}
