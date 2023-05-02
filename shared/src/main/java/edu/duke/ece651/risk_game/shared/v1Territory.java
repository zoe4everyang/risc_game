package edu.duke.ece651.risk_game.shared;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
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

    private int cloaked;

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
        this.cloaked = -1;
    }

    @JsonCreator
    public v1Territory(@JsonProperty("id") int id,
                       @JsonProperty("name") String name,
                       @JsonProperty("owner") int owner,
                       @JsonProperty("troop") Troop units,
                       @JsonProperty("distances") List<Integer> distances,
                       @JsonProperty("cost") int cost,
                       @JsonProperty("techProduction") int tech_prod,
                       @JsonProperty("foodProduction") int food_prod){

        this.id = id;
        this.name = name;
        this.owner = owner;
        this.troop = units;
        this.distances = distances;
        this.cost = cost;
        this.tech_production = tech_prod;
        this.food_production = food_prod;
        this.combatResolver = new v1CombatResolver();
        this.cloaked = -1;
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

    @Override
    public int getTroopSize() {
        return this.troop.getUnits().size();
    }

    @Override
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

    @Override
    public List<Integer> getNeighbours() {
        // return list of neighbours
        ArrayList<Integer> neighbours = new ArrayList<>();
        for (int i = 0; i < distances.size(); i++) {
            if (distances.get(i) == 1) {
                neighbours.add(i);
            }
        }
        return neighbours;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public void upgradeUnit(int UnitId, int amount) {
        // upgrade unit
        this.troop.upgrade(UnitId, amount);
    }

    @Override
    public int getTechProduction() {
        // return tech production
        return tech_production;
    }

    @Override
    public int getFoodProduction() {
        // return food production
        return food_production;
    }

    @Override
    public Troop getTroop() {
        // return list of troops
        return this.troop;
    }

    @Override
    public int getCloak() {
        return cloaked;
    }

    @Override
    public int setCloak(int cloak) {
        return this.cloaked = cloak;
    }

    @Override
    public void reduceCloak() {
        if (this.cloaked > 0) {
            this.cloaked -= 1;
        }
    }


}
