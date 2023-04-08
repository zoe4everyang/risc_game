package edu.duke.ece651.risk_game.shared;

import java.util.List;

/**
 * This class is used to create a message that contains the information of response from the server.
 */
public class Response extends Message {
    //private final String playerName;

    private final List<Territory> territories;
    private final Boolean loseState;
    private final Boolean endState;

    /**
     * This constructor is used to create a message that contains the information of the player's response.
     *
     * @param playerID    the ID of the player
     * @param territories the list of territories that the player owns
     * @param loseState   the result of the player
     *                    (true: the player loses the game; false: the player does not lose the game)
     * @param endState    the state of the game
     *                    (true: the game is List; false: the game is not List)
     */
    public Response(Integer playerID, List<Territory> territories, Boolean loseState, Boolean endState) {
        //public Response(Integer playerID, String playerName, Integer gameState, List<Territory> territories, Boolean gameEnd) {
        super(playerID);
        //this.playerName = playerName;
        this.territories = territories;
        this.loseState = loseState;
        this.endState = endState;
    }

    // This method is used to get the list of territories that the player owns.
    public List<Territory> getTerritories() {
        return territories;
    }

    // This method is used to get the result of the player.
    public Boolean isLose() {
        return loseState;
    }

    // This method is used to get if the game is List.
    public Boolean isEnd() {
        return endState;
    }
}
