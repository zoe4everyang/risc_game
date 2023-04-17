package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller class
 */
public class Controller {
    private final MapFactory mapFactory = new v1MapFactory();
    private int numPlayers;
    private WorldMap world;
    private List<Player> players = new ArrayList<>();
    private List<Territory> territories;

    private ArrayList<Action> moveCache = new ArrayList<>();
    private ArrayList<Action> attackCache = new ArrayList<>();

    private final Map<Integer, Integer> updateRequirement = new HashMap<Integer, Integer>() {{
        put(1, 50);
        put(2, 75);
        put(3, 125);
        put(4, 200);
        put(5, 300);
    }};
    private final Map<Integer, Integer> unitRequirement = new HashMap<Integer, Integer>() {{
        put(0, 3);
        put(1, 8);
        put(2, 19);
        put(3, 25);
        put(4, 35);
        put(5, 50);
    }};
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
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i, "p" + i, new Resource(100, 100)));
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

//    /**
//     * Check if the game is over
//     * @param attackFrom list of attack from
//     * @param attackTo list of attack to
//     * @param attackNum list of attack number
//     * @param moveFrom list of move from
//     * @param moveTo list of move to
//     * @param moveNum list of move number
//     */
//
//    public Boolean step (List<Integer> attackerIds,
//            List<Integer> attackFrom,
//            List<Integer> attackTo,
//            List<Integer> attackNum,
//            List<Integer> moverIds,
//            List<Integer> moveFrom,
//            List<Integer> moveTo,
//            List<Integer> moveNum
//    ) {
//        // make a step
//        world.resolveMove(moverIds, moveFrom, moveTo, moveNum);
//        world.resolveAttack(attackerIds, attackFrom, attackTo, attackNum);
//        return checkEnd();
//    }

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
    public List<Player> getPlayers() {
        return players;
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

    // TODO: Test this shit upgrade unit
    public void upgradeUnit(int playerId, int territoryId, int unitId, int amount) {
        if (territories.get(territoryId).getOwner() == playerId) {
            territories.get(territoryId).upgradeUnit(unitId, amount);
        }
    }

    // TODO: Test this shit upgrade max technology
    public void upgradeMaxTechnology(int playerId) {
        players.get(playerId).upgradeTechLevel(updateRequirement);
        return;
    }

    public Boolean cacheAttack(int playerId, int from, int to, List<Unit> units) {
        Troop t = new unitTroop(playerId, units);
        // if have sufficient resources and units
        // if from and to are neightbouts
        world.getTerritories().get(from).getTroop().isSubsetOfThis(t);
        if (players.get(playerId).getFoodPoint() >= units.size() &&
            world.isNeighbour(from, to) &&
            world.getTerritories().get(from).getOwner() == playerId &&
            world.getTerritories().get(to).getOwner() != playerId) {
            // cache attack operation to cache
            attackCache.add(new Action(from, to, t));
            // reduce the number of units in from territory
            world.getTerritories().get(from).removeTroop(t);
            // reduce resources
            players.get(playerId).reduceFoodPoint(units.size());
            return true;
        }

        return false;

    }

    public Boolean cacheMove(int playerId, int from, int to, List<Unit> units) {
        Troop t = new unitTroop(playerId, units);

        world.getTerritories().get(from).getTroop().isSubsetOfThis(t);
        // check if the move is valid
        if (world.getTerritories().get(from).getOwner() == playerId &&
                world.getTerritories().get(to).getOwner() == playerId &&
                world.isConnected(from, to, playerId) &&
                world.shortestPath(from, to, playerId) * units.size()<= players.get(playerId).getFoodPoint()
            ) {
            // add movement to cache
            Action move = new Action(from, to, t);
            moveCache.add(move);
            // reduce the number of units in from territory
            this.world.makeMove(from, to, t);
            players.get(playerId).reduceFoodPoint(world.shortestPath(from, to, playerId) * units.size());
            return true;
        }
        return false;
    }
    public Boolean cacheUpgradeUnit(int playerId, int territoryId, int unitId, int amount) {
        // check if the upgrade is valid
        // check if the resources is sufficient
        // get current level of the unit
        int currentLevel = territories.get(territoryId).getTroop().getUnit(unitId).getLevel();
        int totalCost = 0;
        for (int i = 0; i < amount; i++) {
            totalCost += unitRequirement.get(currentLevel + i);
        }
        if (players.get(playerId).getFoodPoint() >= totalCost &&
                players.get(playerId).getTechLevel() >= currentLevel + amount &&
                territories.get(territoryId).getOwner() == playerId) {
            // upgrade unit
            upgradeUnit(playerId, territoryId, unitId, amount);
            // reduce resources
            players.get(playerId).reduceTechPoint(totalCost);
            return true;
        }
        return false;
    }

    public Boolean cacheUpgradeTechnology(int playerId) {
        if (players.get(playerId).getTechLevel() < 6) {
            upgradeMaxTechnology(playerId);
            return true;
        }
        return false;
    }

    public Boolean commit() {
        // resolve attacks
        for (Action a : attackCache) {
            if (world.getTerritories().get(a.from).getOwner() !=
                    world.getTerritories().get(a.to).getOwner()) {
                world.makeAttack(a.to, a.units);
            } else {
                world.getTerritories().get(a.to).addTroop(a.units);
            }
        }
        // resolve moves
        for (Player p : players) {
            p.commitUpgrade();
        }
        attackCache.clear();
        moveCache.clear();


        // generate new resources
        for (Territory t : world.getTerritories()) {
            t.getTroop().addUnit(new Unit("alili", 0));
            players.get(t.getOwner()).addFoodPoint(t.getFoodProduction());
            players.get(t.getOwner()).addTechPoint(t.getTechProduction());
        }
        // generate new units
        return true;
    }







}
