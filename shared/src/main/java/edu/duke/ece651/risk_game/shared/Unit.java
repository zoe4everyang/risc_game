package edu.duke.ece651.risk_game.shared;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Unit {
    private String name;
    private int level;
    private int unitId;
    private static int unitIdCounter = 0;
    public Unit(String name, int level) {
        this.name = name;
        this.level = level;
        this.unitId = unitIdCounter;
        unitIdCounter++;
    }

    @JsonCreator
    public Unit(@JsonProperty("name") String name,
                @JsonProperty("level") int level,
                @JsonProperty("unitId") int unitId) {
        this.name = name;
        this.level = level;
        this.unitId = unitId;
    }


    public Unit(String name) {
        this.name = name;
        this.level = 0;
        this.unitId = unitIdCounter;
        unitIdCounter++;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getUnitId() {
        return unitId;
    }
    public void upgrade(int amount) {
        level += amount;
    }
}
