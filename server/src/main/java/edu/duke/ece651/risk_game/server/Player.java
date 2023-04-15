package edu.duke.ece651.risk_game.server;

import edu.duke.ece651.risk_game.shared.Resource;
import edu.duke.ece651.risk_game.shared.Territory;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Player {
    private List<Integer> territories;
    private Resource resource;
    private int playerId;
    private String playerName;
    private int tech_level;
    private Boolean pendingUpdate;

    public Player(int playerId, String playerName, List<Integer> territories, Resource resource) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.territories = territoriesgit;
        this.resource = resource;
        this.tech_level = 1;
        this.pendingUpdate = false;
    }

    public Boolean upgradeTechLevel(Map<Integer, Integer> updateRequirement){
        //upgrade tech level
        if (resource.getTechPoint() > updateRequirement.get(tech_level)){
            pendingUpdate = true;
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

}
