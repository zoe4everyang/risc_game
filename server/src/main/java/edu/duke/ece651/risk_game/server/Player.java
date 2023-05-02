package edu.duke.ece651.risk_game.server;

import edu.duke.ece651.risk_game.shared.Resource;
import edu.duke.ece651.risk_game.shared.Territory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Player {
    private Resource resource;
    private int playerId;
    private String playerName;
    private int tech_level;
    private Boolean pendingUpdate;

    private ArrayList<Boolean> visible;

    private ArrayList<Boolean> visited;


    private int spyPosition;

    public Player(int playerId, String playerName, Resource resource, int numTerritory) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.spyPosition = -1;
        this.resource = resource;
        this.tech_level = 1;
        this.pendingUpdate = false;
        // array list with all false and length numTerritory
        this.visible = new ArrayList<>();
        this.visited = new ArrayList<>();
        for (int i = 0; i < numTerritory; i++) {
            this.visible.add(false);
            this.visited.add(false);
        }
    }

    public Boolean upgradeTechLevel(Map<Integer, Integer> updateRequirement){
        //upgrade tech level
        if (resource.getTechPoint() >= updateRequirement.get(tech_level) &&
                pendingUpdate == false){
            pendingUpdate = true;
            resource.reduceTechPoint(updateRequirement.get(tech_level));
            return true;
        }
        return false;
    }

    public void commitUpgrade() {
        //commit upgrade
        if (pendingUpdate) {
            tech_level++;
            pendingUpdate = false;
        }
    }

    public int getTechLevel() {
        return tech_level;
    }
    public void setVisible(int terrId) {
        visible.set(terrId, true);
    }

    public void setInvisible(int terrId) {
        visible.set(terrId, false);
    }
    public int getFoodPoint() {
        return resource.getFoodPoint();
    }

    public int getTechPoint() {
        return resource.getTechPoint();
    }

    public void addFoodPoint(int foodPoint) {
        resource.addFoodPoint(foodPoint);
    }

    public void addTechPoint(int techPoint) {
        resource.addTechPoint(techPoint);
    }


    public Boolean reduceFoodPoint(int foodPoint) {
        return resource.reduceFoodPoint(foodPoint);
    }

    public Boolean reduceTechPoint(int techPoint) {
        return resource.reduceTechPoint(techPoint);
    }

    public Boolean hasSpy() {
        return spyPosition != -1;
    }

    public int getSpyPos() {
        return spyPosition;
    }

    public void setVisited(int terrId) {
        visited.set(terrId, true);
    }

    public List<Boolean> getVisited() {
        return visited;
    }

    public List<Boolean> getVisible() {
        return visible;
    }

    public void setSpyPos(int pos) {
        spyPosition = pos;
    }


}
