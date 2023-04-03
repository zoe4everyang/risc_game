package edu.duke.ece651.risk_game.server;
import java.util.List;
public class v1Territory implements Territory{
    private int id;
    private String name;
    private int owner;
    private int units;
    private List<Integer> distances;
    private CombatResolver combatResolver;

    v1Territory(int id, String name, int owner, List<Integer> distances, CombatResolver combatResolver) {
        // constructor
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.units = 0;
        this.distances = distances;
        this.combatResolver = combatResolver;
    }


    public void addUnit(int unit) {
        // add unit to territory
        this.units += unit;
    }
    public void removeUnit(int unit) {
        // remove unit from territory
        this.units -= unit;
    }
    public void defence(int attacker, int unit) {
        // defend against attacker
        int result = combatResolver.resolveCombat(unit, this.units);
        if (result > 0) {
            // attacker wins
            this.owner = attacker;
            this.units = result;
        } else if (result < 0) {
            // defender wins
            this.units = -result;
        }
    }
    public List<Integer> getDistances() {
        // return list of distances to other territories
        return distances;
    }

    public int getID() {
        // return territory ID
        return id;
    }

    public String getName() {
        // return territory name
        return name;
    }

    public int getOwner() {
        // return territory owner
        return owner;
    }

    public int getUnits() {
        // return number of units in territory
        return units;
    }

}
