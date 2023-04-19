package edu.duke.ece651.risk_game.shared;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = unitTroop.class, name = "unitTroop")
})

public interface Troop {
    public int getOwner();

    public void deleteUnit(int unitId);
    public void addUnit(Unit unit);

    public void addUnits(List<Unit> units);

    public void addTroop(Troop troop);

    void isSubsetOfThis(Troop troop);

    public void removeTroop(Troop troop);

    public void setOwner(int owner);

    public List<Unit> getUnits();

    public boolean upgrade(int unitId, int amount);


    public Unit getUnit(int UnitId);
}
