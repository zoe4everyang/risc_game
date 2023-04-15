package edu.duke.ece651.risk_game.shared;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Resource {
    private final Integer techPoint;
    private final Integer foodPoint;

    /**
     * This constructor is used to create a message that contains the information of the player's resource.
     * @param techPoint the tech point of the player
     * @param foodPoint the food point of the player
     */
    public Resource(@JsonProperty("techPoint") Integer techPoint,
                    @JsonProperty("foodPoint") Integer foodPoint) {
        this.techPoint = techPoint;
        this.foodPoint = foodPoint;
    }

    // This method is used to get the tech point of the player.
    public Integer getTechPoint() {
        return techPoint;
    }

    // This method is used to get the food point of the player.
    public Integer getFoodPoint() {
        return foodPoint;
    }
}
