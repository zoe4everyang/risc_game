package edu.duke.ece651.risk_game.shared;

public class Unit {
    private String name;
    private int level;
    private int unitId;
    public Unit(String name, int level, int unitId) {
        this.name = name;
        this.level = level;
        this.unitId = unitId;
    }
    public Unit(String name, int unitId) {
        this.name = name;
        this.level = 0;
        this.unitId = unitId;
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
    public void upgrade() {
        level++;
    }
}
