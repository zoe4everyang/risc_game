package edu.duke.ece651.risk_game.shared;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used to create a message that contains the information of the player.
 */
public class PlayerInfo {
    private final Integer playerID;
    private final Resource resource;
    private final Integer techLevel;

    /**
     * This constructor is used to create a message that contains the information of the player.
     * @param playerID the ID of the player
     * @param resource the resource of the player
     * @param techLevel the tech level of the player
     */
    public PlayerInfo(@JsonProperty("playerID") Integer playerID,
                      @JsonProperty("resource") Resource resource,
                      @JsonProperty("techLevel") Integer techLevel) {
        this.playerID = playerID;
        this.resource = resource;
        this.techLevel = techLevel;
    }

    // This method is used to get the ID of the player.
    public Integer getPlayerID() {
        return playerID;
    }

    // This method is used to get the resource of the player.
    public Resource getResource() {
        return resource;
    }

    // This method is used to get the tech level of the player.
    public Integer getTechLevel() {
        return techLevel;
    }
}
