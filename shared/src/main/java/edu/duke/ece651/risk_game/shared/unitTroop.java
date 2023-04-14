package edu.duke.ece651.risk_game.shared;

import java.util.List;

public class unitTroop implements Troop{
    private int owner;
    private List<Unit> units;
    @Override
    public int getOwner() {
        return owner;
    }
    @Override
    public List<Unit> getUnits() {
        return units;
    }

    @Override
    public void deleteUnit(int unitId) {
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getUnitId() == unitId) {
                units.remove(i);
                break;
            }
        }
    }

    @Override
    public void addUnit(Unit unit) {
        units.add(unit);
    }

    @Override
    public void addUnits(List<Unit> units) {
        this.units.addAll(units);
    }

    @Override
    public void setOwner(int owner) {
        this.owner = owner;
    }

    @Override
    public boolean upgrade(int unitId) {
        units.get(unitId).upgrade();
        return true;
    }
}
