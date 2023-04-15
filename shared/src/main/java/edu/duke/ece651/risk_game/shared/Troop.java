package edu.duke.ece651.risk_game.shared;

import java.util.List;

public interface Troop {
    public int getOwner();

    public void deleteUnit(int unitId);
    public void addUnit(Unit unit);

    public void addUnits(List<Unit> units);

    public void setOwner(int owner);

    public List<Unit> getUnits();

    boolean upgrade(int unitId, int amount);
}
