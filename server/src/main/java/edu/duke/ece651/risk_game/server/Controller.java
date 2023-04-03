package edu.duke.ece651.risk_game.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
public class Controller {
    // TODO: write test cases for the CONTROLLER
    private MapFactory mapFactory;
    private int numPlayers;
    private List<Territory> territories;
    Controller(int numPlayers, MapFactory mapFactory) {
        // constructor
        this.mapFactory = mapFactory;
        this.numPlayers = numPlayers;
        if (numPlayers == 2) {
            this.territories = mapFactory.make2PlayerMap();
        } else if (numPlayers == 3) {
            this.territories = mapFactory.make3PlayerMap();
        } else if (numPlayers == 4) {
            this.territories = mapFactory.make4PlayerMap();
        } else if (numPlayers == 5) {
            this.territories = mapFactory.make5PlayerMap();
        } else {
            throw new IllegalArgumentException("Invalid number of players");
        }
    }
    Controller(List<Territory> territories) {
        // constructor
        this.territories = territories;
    }
    // private List<Player> players;
    private void makeMove(int playerId, int fromId, int toId, int unitNum) {
        // make move
        // TODO: check if move is valid
        territories.get(fromId).removeUnit(unitNum);
        territories.get(toId).addUnit(unitNum);
    }

    private void makeAttack(int playerId, int fromId, int toId, int unitNum) {
        // make attack
        // TODO: check if attack is valid
        territories.get(fromId).removeUnit(unitNum);
        territories.get(toId).defence(playerId, unitNum);
    }

    private void resolveAttack(List<Integer> playerIds, 
            List<Integer> fromIds, 
            List<Integer> toIds, 
            List<Integer> unitNums) {
        // resolve attack
        for (int i = 0; i < playerIds.size(); i++) {
            makeAttack(playerIds.get(i), fromIds.get(i), toIds.get(i), unitNums.get(i));
        }
    }

    private void resolveMove(List<Integer> playerIds, 
            List<Integer> fromIds, 
            List<Integer> toIds, 
            List<Integer> unitNums
            ) {
        // resolve move
        for (int i = 0; i < playerIds.size(); i++) {
            makeMove(playerIds.get(i), fromIds.get(i), toIds.get(i), unitNums.get(i));
        }
        
    }

    public void initGame(List<Integer> unitPlacement) {
        // init game
        for (int i = 0; i < territories.size(); i++) {
            territories.get(i).addUnit(unitPlacement.get(i));
        }
    }

    public Boolean step (List<Integer> attackerIds, 
            List<Integer> attackFrom, 
            List<Integer> attackTo, 
            List<Integer> attackNum, 
            List<Integer> moverIds, 
            List<Integer> moveFrom, 
            List<Integer> moveTo, 
            List<Integer> moveNum
    ) {
        // make a step
        resolveMove(moverIds, moveFrom, moveTo, moveNum);
        resolveAttack(attackerIds, attackFrom, attackTo, attackNum);
        return checkEnd();
    }

    public List<Integer> getLosers() {
        // return list of losers
        ArrayList<Boolean> isLosed = new ArrayList<Boolean>();
        for (int i = 0; i < numPlayers; i++) {
            isLosed.add(true);
        }
        for (int i = 0; i < territories.size(); i++) {
            isLosed.set(territories.get(i).getOwner(), false);
        }
        ArrayList<Integer> losers = new ArrayList<Integer>();
        for (int i = 0; i < numPlayers; i++) {
            if (isLosed.get(i)) {
                losers.add(i);
            }
        }
        return losers; 
    }

    public int getWinner() {
        // return winner
        if (checkEnd()) {
            return territories.get(0).getOwner();
        }
        return -1;
    }

    public List<Territory> getTerritories() {
        // return list of territories
        return territories;
    }

    public Boolean checkEnd() {
        // check if game ends
        HashSet<Integer> owners = new HashSet<>();
        for (int i = 0; i < territories.size(); i++) {
            owners.add(territories.get(i).getOwner());
        }
        return owners.size() == 1;
    }


}
