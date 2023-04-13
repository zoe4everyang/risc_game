package edu.duke.ece651.risk_game.shared;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

/**
 * This interface is used to represent a territory.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = v1Territory.class, name = "v1")
})
public interface Territory {
    public void addUnit(int unit);
    public void removeUnit(int unit);
    public void defence(int attacker, int unit);
    public List<Integer> getDistances(); // returns a list of distances to other territories'
    public int getID();
    public String getName();
    public int getOwner();
    public int getUnits();
}