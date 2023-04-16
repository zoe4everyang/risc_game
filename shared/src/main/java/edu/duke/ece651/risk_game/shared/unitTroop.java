package edu.duke.ece651.risk_game.shared;

import java.util.ArrayList;
import java.util.List;

public class unitTroop implements Troop{
    private int owner;
    private List<Unit> units;

    public unitTroop(int owner, List<Unit> units) {
        this.owner = owner;
        this.units = new ArrayList<>();
        for (Unit unit : units) {
            this.units.add(unit);
        }
    }

    public unitTroop(int owner) {
        this.owner = owner;
        this.units = new ArrayList<>();
    }

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
                return;
            }
        }
        throw new IllegalArgumentException("unitId not found");
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
    public void addTroop(Troop troop) {
        addUnits(troop.getUnits());
    }

    private boolean unitIsSubsetOfTroop(Unit unit, Troop troop) {
        for (Unit troopUnit : troop.getUnits()) {
            if (troopUnit.getUnitId() == unit.getUnitId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeTroop(Troop troop) {
        for (Unit unit : troop.getUnits()) {
            if (!unitIsSubsetOfTroop(unit, this)) {
                throw new IllegalArgumentException("unit is not a subset of this troop");
            }
        }
        for (Unit unit : troop.getUnits()) {
            deleteUnit(unit.getUnitId());
        }
    }

    @Override
    public void setOwner(int owner) {
        this.owner = owner;
    }

    @Override
    public boolean upgrade(int unitId, int amount) {
        for (Unit unit : units) {
            if (unit.getUnitId() == unitId) {
                unit.upgrade(amount);
                return true;
            }
        }
        return false;
    }
}
