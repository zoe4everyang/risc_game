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
    public boolean upgrade(int unitId) {
        units.get(unitId).upgrade();
        return true;
    }
}
