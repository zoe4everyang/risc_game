package edu.duke.ece651.risk_game.shared;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
     * @param territories   the list of territories that the player owns
     * @param lose     the result of the player
     *                      (true: the player loses the game; false: the player does not lose the game)
     * @param end      the state of the game
     *                      (true: the game is end; false: the game is not end)
     * @param unitAvailable the number of units that the player can place on the territories
     */
    @JsonCreator
    public InitResponse(@JsonProperty("playerID") Integer playerID,
                        @JsonProperty("territories") List<Territory> territories,
                        @JsonProperty("lose") Boolean lose,
                        @JsonProperty("end") Boolean end,
                        @JsonProperty("unitAvailable") Integer unitAvailable) {
        super(playerID, territories, lose, end);
        this.unitAvailable = unitAvailable;
    }

    // This method is used to get the number of units that the player can place on the territories.
    public Integer getUnitAvailable() {
        return unitAvailable;
    }
}
