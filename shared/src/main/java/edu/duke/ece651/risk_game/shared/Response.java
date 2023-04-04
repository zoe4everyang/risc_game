package edu.duke.ece651.risk_game.shared;

import java.util.List;

/**
 * This class is used to create a message that contains the information of response from the server.
 */
public class Response extends Message{
    private final String playerName;
    private final Integer gameState;
    private final List<Territory> territories;

    /**
     * This constructor is used to create a message that contains the information of the player's response.
     *
     * @param playerID the ID of the player
     * @param playerName the name of the player
     * @param territories the list of territories that the player owns
     */
    public Response(Integer playerID, String playerName, Integer gameState, List<Territory> territories) {
        super(playerID);
        this.playerName = playerName;
        this.gameState = gameState;
        this.territories = territories;
    }

    // This method is used to get the name of the player.
    public String getPlayerName() {
        return playerName;
    }

    // This method is used to get the state of the game.
    public Integer getGameState() {
        return gameState;
    }

    // This method is used to get the list of territories that the player owns.
    public List<Territory> getTerritories() {
        return territories;
    }

    // This method is used to get the number of territories that the player owns.
    public Integer getTerritoryNum() {
        return territories.size();
    }
}
