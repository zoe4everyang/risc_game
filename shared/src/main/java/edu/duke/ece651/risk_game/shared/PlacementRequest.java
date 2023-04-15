package edu.duke.ece651.risk_game.shared;
import java.util.List;

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
     * @param playerId the ID of the player
     * @param placement the list of numbers of units that the player wants to place on each territory
     */
    public PlacementRequest(Integer playerId, List<Integer> placement) {
        super(playerId);
        this.placement = placement;
    }


    // This method is used to get the list of numbers of units that the player wants to place on each territory.
    public List<Integer> getPlacement() {
        return placement;
    }
}
