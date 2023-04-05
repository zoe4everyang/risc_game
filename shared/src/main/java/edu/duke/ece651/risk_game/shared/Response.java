package edu.duke.ece651.risk_game.shared;

import java.util.List;

/**
 * This class is used to create a message that contains the information of response from the server.
 */
public class Response extends Message{
    //private final String playerName;

    private final List<Territory> territories;
    private final Boolean winState;
    private final Boolean endState;

    /**
     * This constructor is used to create a message that contains the information of the player's response.
     *
     * @param playerID the ID of the player
//     * @param playerName the name of the player
     * @param territories the list of territories that the player owns
     */
    public Response(Integer playerID, List<Territory> territories, Boolean winState, Boolean endState) {
    //public Response(Integer playerID, String playerName, Integer gameState, List<Territory> territories, Boolean gameEnd) {
        super(playerID);
        //this.playerName = playerName;
        this.territories = territories;
        this.winState = winState;
        this.endState = endState;
    }

    // This method is used to get the name of the player.
//    public String getPlayerName() {
//        return playerName;
//    }

    // This method is used to get the state of the game.
    public Integer getGameState() {
        return gameState;
    }

    // This method is used to get the list of territories that the player owns.
    public List<Territory> getTerritories() {
        return territories;
    }

    // This method is used to get the result of the player.
    public Boolean isWin() {
        return winState;
    }

    // This method is used to get if the game is end.
    public Boolean isEnd() {
        return endState;
    }
}
