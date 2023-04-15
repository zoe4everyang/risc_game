package edu.duke.ece651.risk_game.shared;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Resource {
    private Integer techPoint;
    private Integer foodPoint;

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

    // This method is used to add the tech point of the player.
    public void addTechPoint(Integer techPoint) {
        this.techPoint += techPoint;
    }
    // This method is used to add the food point of the player.
    public void addFoodPoint(Integer foodPoint) {
        this.foodPoint += foodPoint;
    }

    // This method is used to reduce the tech point of the player.
    public Boolean reduceTechPoint(Integer techPoint) {
        if (this.techPoint < techPoint) {
            return false;
        }
        this.techPoint -= techPoint;
        return true;
    }

    // This method is used to reduce the food point of the player.
    public Boolean reduceFoodPoint(Integer foodPoint) {
        if (this.foodPoint < foodPoint) {
            return false;
        }
        this.foodPoint -= foodPoint;
        return true;
    }

}
