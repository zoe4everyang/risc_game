package edu.duke.ece651.risk_game.shared;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * This class is used to create a message that contains the information of the player.
 */
public class PlayerInfo {
    private final Integer playerID;
    private final Resource resource;
    private final Integer techLevel;

    private final List<Boolean> visible;
    private final List<Boolean> visited;

    private final Integer spyPosition;
    private final Boolean hasSpy;

    private final Boolean canCloak;

    /**
     * This constructor is used to create a message that contains the information of the player.
     * @param playerID the ID of the player
     * @param resource the resource of the player
     * @param techLevel the tech level of the player
     */
    public PlayerInfo(@JsonProperty("playerID") Integer playerID,
                      @JsonProperty("resource") Resource resource,
                      @JsonProperty("techLevel") Integer techLevel,
                      @JsonProperty("visible") List<Boolean> visible,
                      @JsonProperty("visited") List<Boolean> visited,
                      @JsonProperty("spyPosition") Integer spyPosition,
                      @JsonProperty("hasSpy") Boolean hasSpy,
                      @JsonProperty("canCloak") Boolean canCloak) {
        this.playerID = playerID;
        this.resource = resource;
        this.techLevel = techLevel;
        this.visible = visible;
        this.visited = visited;
        this.spyPosition = spyPosition;
        this.hasSpy = hasSpy;
        this.canCloak = canCloak;

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
    // This method is used to get the visible of the player.
    public List<Boolean> getVisible() {
        return visible;
    }
    // This method is used to get the visited of the player.
    public List<Boolean> getVisited() {
        return visited;
    }
    // This method is used to get the spy position of the player.

    public Integer getSpyPosition() {
        return spyPosition;
    }
    // This method is used to get the hasSpy the player.
    public Boolean getHasSpy() {
        return hasSpy;
    }
    // This method is used to get the canCloak the player.
    public Boolean getCanCloak() {
        return canCloak;
    }
}
