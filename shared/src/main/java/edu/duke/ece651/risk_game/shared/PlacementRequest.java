package edu.duke.ece651.risk_game.shared;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used to create a message that contains the information of the player's placement.
 * The message contains the information of the player's placement.
 * The player can place units on the territories that he owns.
 */
public class PlacementRequest extends Message{
    private final List<Integer> placement;

    /**
     * This constructor is used to create a message that contains the information of the player's placement.
     *
     * @param playerID the ID of the player
     * @param placement the list of numbers of units that the player wants to place on each territory
     */
    public PlacementRequest(Integer playerID, List<Integer> placement) {
        super(playerID);
        this.placement = placement;
    }

    @JsonCreator
    public PlacementRequest(@JsonProperty("playerInfo") PlayerInfo playerInfo,
                            @JsonProperty("placement") List<Integer> placement) {
        super(playerInfo);
        this.placement = placement;
    }

    // This method is used to get the list of numbers of units that the player wants to place on each territory.
    public List<Integer> getPlacement() {
        return placement;
    }
}
