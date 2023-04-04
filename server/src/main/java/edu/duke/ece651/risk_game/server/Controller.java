package edu.duke.ece651.risk_game.server;

import java.util.List;

public class Controller {
    // TODO: write test cases for the CONTROLLER
    private MapFactory mapFactory;
    private int numPlayers;
    private WorldMap world;
    private List<Territory> territories;
    Controller(int numPlayers, MapFactory mapFactory) {
        // constructor
        this.mapFactory = mapFactory;
        if (numPlayers == 2) {
            this.world = mapFactory.make2PlayerMap();
        } else if (numPlayers == 3) {
            this.world = mapFactory.make3PlayerMap();
        } else if (numPlayers == 4) {
            this.world = mapFactory.make4PlayerMap();
        } else if (numPlayers == 5) {
            this.world = mapFactory.make5PlayerMap();
        } else {
            throw new IllegalArgumentException("Invalid number of players");
        }
        this.territories = world.getTerritories();
    }
    Controller(List<Territory> territories) {
        // constructor
        this.territories = territories;
    }


    private void resolveAttack(List<Integer> playerIds, 
            List<Integer> fromIds, 
            List<Integer> toIds, 
            List<Integer> unitNums) {
        // resolve attack
        for (int i = 0; i < playerIds.size(); i++) {
           world.makeAttack(playerIds.get(i), fromIds.get(i), toIds.get(i), unitNums.get(i));
        }
    }

    private void resolveMove(List<Integer> playerIds, 
            List<Integer> fromIds, 
            List<Integer> toIds, 
            List<Integer> unitNums
            ) {
        // resolve move
        for (int i = 0; i < playerIds.size(); i++) {
            world.makeMove(playerIds.get(i), fromIds.get(i), toIds.get(i), unitNums.get(i));
        }
        
    }

    public void initGame(List<Integer> unitPlacement) {
        // init game
        world.setUnits(unitPlacement);
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
        return world.getLosers(); 
    }

    public int getWinner() {
        return world.getWinner();
    }

    public List<Territory> getTerritories() {
        // return list of territories
        return territories;
    }

    public Boolean checkEnd() {
        // check if game ends
        return world.checkEnd();
    }


}
