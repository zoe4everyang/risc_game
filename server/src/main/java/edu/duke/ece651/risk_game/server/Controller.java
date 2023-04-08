package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;
import java.util.List;

/**
 * Controller class
 */
public class Controller {
    private final MapFactory mapFactory = new v1MapFactory();
    private int numPlayers;
    private WorldMap world;
    private List<Territory> territories;

    /**
     * Constructor
     * @param numPlayers number of players
     */
    Controller(int numPlayers) {
        // constructor
        this.numPlayers = numPlayers;
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

    /**
     * Constructor
     * @param territories list of territories
     */
    Controller(List<Territory> territories) {
        // constructor
        this.territories = territories;
    }

    /**
     * Check if the game is over
     * @param unitPlacement list of units
     */
    public void initGame(List<Integer> unitPlacement) {
        // init game
        world.setUnits(unitPlacement);
    }

    /**
     * Check if the game is over
     * @param attackFrom list of attack from
     * @param attackTo list of attack to
     * @param attackNum list of attack number
     * @param moveFrom list of move from
     * @param moveTo list of move to
     * @param moveNum list of move number
     */

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
        world.resolveMove(moverIds, moveFrom, moveTo, moveNum);
        world.resolveAttack(attackerIds, attackFrom, attackTo, attackNum);
        return checkEnd();
    }

    /**
     * Check if the game is over
     */
    public List<Integer> getLosers() {
        return world.getLosers(); 
    }

    /**
     * Check if the game is over
     */
    public int getWinner() {
        return world.getWinner();
    }

    /**
     * Check if the game is over
     */
    public List<Territory> getTerritories() {
        // return list of territories
        return territories;
    }

    /**
     * Check if the game is over
     */
    public Boolean checkEnd() {
        // check if game ends
        return world.checkEnd();
    }

    /**
     * Check if the game is over
     * @param playerId player id
     */
    public int getPlayers() {
        return numPlayers;
    }
    public Boolean checkWin(int playerId) {
        return playerId == getWinner();
    }

    /**
     * Check if the game is over
     * @param playerId player id
     */
    public Boolean checkLose(int playerId) {
        return getLosers().contains(playerId);
    }

    /**
     * Check if the game is over
     */
    public int getUnitAvailable() {
        return world.getUnitAvailable();
    }


}
