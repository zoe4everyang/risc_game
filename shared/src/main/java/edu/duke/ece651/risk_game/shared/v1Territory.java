package edu.duke.ece651.risk_game.shared;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class v1Territory implements Territory{
    private final String type = "v1";
    private int id;
    private String name;
    private int owner;
    private Troop troop;
    private List<Integer> distances;
    private CombatResolver combatResolver;

    public v1Territory(int id, String name, int owner, List<Integer> distances, CombatResolver combatResolver) {
        // constructor
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.troop = new unitTroop(owner);
        this.distances = distances;
        this.combatResolver = combatResolver;
    }

    @JsonCreator
    public v1Territory(@JsonProperty("id") int id,
                       @JsonProperty("name") String name,
                       @JsonProperty("owner") int owner,
                       @JsonProperty("troop") Troop units,
                       @JsonProperty("distances") List<Integer> distances) {
        // constructor
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.troop = units;
        this.distances = distances;
        this.combatResolver = new v1CombatResolver();
    }

    @Override
    public void addTroop(Troop troop) {
        // add unit to territory
        this.troop.addTroop(troop);
    }
    @Override
    public void removeTroop(Troop troop) {
        // remove unit from territory
        this.troop.removeTroop(troop);
    }
    
    public void defence(Troop troop) {
        // TODO: finalize defence after reolve combat is done
//        // defend against attacker
//        int result = combatResolver.resolveCombat(unit, this.units);
//        if (result > 0) {
//            // attacker wins
//            this.owner = attacker;
//            this.units = result;
//        } else if (result < 0) {
//            // defender wins
//            this.units = -result;
//        }
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



}
