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

    private int cost;

    private int tech_production;
    private int food_production;

    public v1Territory(int id, String name,
                       int owner, List<Integer> distances,
                       int cost, int tech_prod, int food_prod,
                       CombatResolver combatResolver) {
        // constructor
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.troop = new unitTroop(owner);
        this.distances = distances;
        this.combatResolver = combatResolver;
        this.cost = cost;
        this.tech_production = tech_prod;
        this.food_production = food_prod;
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
    
    public void defence(Troop attackTroop) {
        // defend against attacker
        Troop winner = combatResolver.resolveCombat(attackTroop, this.troop);
        if (winner.getOwner() != this.owner) {
            // attacker winner
            this.troop = attackTroop;
            this.owner = this.troop.getOwner();

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



}
