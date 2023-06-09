package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;
import org.checkerframework.checker.units.qual.A;

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
    private final int cloakRounds = 3;
    private final int cloakCost = 20;
    private final int upgradeCloakCost = 100;
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
        this.territories = world.getTerritories();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i, "p" + i, new Resource(100, 100),
                    this.world.getNumTerritories()));
        }
        for (int i = 0; i < numPlayers; i++) {
            initPlayerVisibility(i);
        }


    }
    protected void initPlayerVisibility(int playerId) {
        // set the territory owned by player Id to visible
        for (Territory t : territories) {
            if (t.getOwner() == playerId) {
                players.get(playerId).setVisible(t.getID());
                players.get(playerId).setVisited(t.getID());
            }
        }
        // set the territory neighbor to visible
        for (Territory t : territories) {
            if (t.getOwner() == playerId) {
                for (int neighborId : t.getNeighbours()) {
                    players.get(playerId).setVisible(neighborId);
                    players.get(playerId).setVisited(neighborId);
                }
            }
        }
    }


    protected void resetVisibility(int playerId) {
        // set visible to all false
        for (Territory t : territories) {
            players.get(playerId).setInvisible(t.getID());
        }
        initPlayerVisibility(playerId);
        // set cloaked territory to invisible
        for (Territory t : territories) {
            if (t.getCloak() > 0 && t.getOwner() != playerId) {
                players.get(playerId).setInvisible(t.getID());
            }
        }
        // set spy territory to visible
        if (players.get(playerId).hasSpy()) {
            players.get(playerId).setVisible(players.get(playerId).getSpyPos());
            players.get(playerId).setVisited(players.get(playerId).getSpyPos());
        }

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


    public void upgradeUnit(int playerId, int territoryId, int unitId, int amount) {
        if (territories.get(territoryId).getOwner() == playerId) {
            territories.get(territoryId).upgradeUnit(unitId, amount);
        }
    }


    public void upgradeMaxTechnology(int playerId) {
        players.get(playerId).upgradeTechLevel(updateRequirement);
        return;
    }

    public Boolean cAttack(int playerId, int from, int to, List<Unit> units) {
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
    private List<Unit> getUnitsByLevel(int territoryId, int level, int amount) {
        List<Unit> units = new ArrayList<>();
        for (Unit u : territories.get(territoryId).getTroop().getUnits()) {
            if (u.getLevel() == level && units.size() < amount) {
                units.add(u);
            }
        }
        return units;
    }
    private List<Unit> selectUnitsByLevel(int territoryId, List<Integer> levels) {
        List<Unit> units = new ArrayList<>();
        for (int l = 0; l < levels.size(); ++l) {
            units.addAll(getUnitsByLevel(territoryId, l, levels.get(l)));
        }
        return units;
    }

    public Boolean cacheAttack(int playerId, int from, int to, List<Integer> levels) {
        List<Unit> units = selectUnitsByLevel(from, levels);
        int total = 0;
        for (int l : levels) {
            total += l;
        }
        if (units.size() < total) {
            return false;
        }
        return cAttack(playerId, from, to, units);
    }

    public Boolean cacheMove(int playerId, int from, int to, List<Integer> levels) {
        List<Unit> units = selectUnitsByLevel(from, levels);
        int total = 0;
        for (int l : levels) {
            total += l;
        }
        if (units.size() < total) {
            return false;
        }
        return cMove(playerId, from, to, units);
    }

    public Boolean cMove(int playerId, int from, int to, List<Unit> units) {
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

    public Boolean cUpgradeUnit(int playerId, int territoryId, int unitId, int amount) {
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

    public Boolean cacheUpgradeUnit(int playerId, int territoryId, int level, int amount, int numUnits) {
        int totalCost = 0;
        for (int i = 0; i < amount; i++) {
            totalCost += unitRequirement.get(level + i);
        }
        totalCost *= numUnits;
        List<Unit> units = getUnitsByLevel(territoryId, level, numUnits);
        if (units.size() < numUnits) {
            return false;
        }


        if (players.get(playerId).getFoodPoint() >= totalCost &&
                players.get(playerId).getTechLevel() >= level + amount &&
                territories.get(territoryId).getOwner() == playerId) {
            // upgrade unit
            for (Unit u : units) {
                int unitId = u.getUnitId();
                upgradeUnit(playerId, territoryId, unitId, amount);
                // reduce resources
                players.get(playerId).reduceTechPoint(totalCost);
            }

        } else {
            return false;
        }


        return true;
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
        // generate new units
        for (Territory t : world.getTerritories()) {
            t.getTroop().addUnit(new Unit("alili", 0));
            players.get(t.getOwner()).addFoodPoint(t.getFoodProduction());
            players.get(t.getOwner()).addTechPoint(t.getTechProduction());
        }
        // reduce cloak remains by 1
        for (Territory t : world.getTerritories()) {
            t.reduceCloak();
        }

        // reset visibility of all players
        for (int i = 0; i < players.size(); ++i) {
            resetVisibility(i);
        }


        return true;
    }

    public PlayerInfo getPlayerInfo(int playerId) {
        return new PlayerInfo(playerId,
                new Resource(players.get(playerId).getTechPoint(),
                        players.get(playerId).getFoodPoint()),
                players.get(playerId).getTechLevel(),
                players.get(playerId).getVisible(),
                players.get(playerId).getVisited(),
                players.get(playerId).getSpyPos(),
                players.get(playerId).hasSpy(),
                players.get(playerId).getCanCloak());

    }

    public List<Integer> getTroopInfo(int territoryId) {
        List<Integer> troopInfo = new ArrayList<>();
        for (Unit u : territories.get(territoryId).getTroop().getUnits()) {
            troopInfo.add(u.getLevel());
        }
        return troopInfo;
    }

    public void cSetCloak(int territoryId) {
            world.setCloak(territoryId, cloakRounds);
    }

    public void cSetSpyPos(int playerId, int spyPos) {
        players.get(playerId).setSpyPos(spyPos);
    }


    public Boolean cacheUpgradeCloak(int playerId) {
        if (players.get(playerId).getTechLevel() >= 3 &&
                players.get(playerId).getTechPoint() >= 100) {
            players.get(playerId).setCanCloak(true);
            // reduce tech point by 100
            players.get(playerId).reduceTechPoint(100);
            return true;
        }
        return false;
    }


    public Boolean cacheUpgradeSpy(int playerId, int territoryId, int level) {
        if (players.get(playerId).getTechLevel() >= 1 &&
                players.get(playerId).getTechPoint() >= 20) {
            // remove a unit with tge given level from the territory
            List<Unit> units = getUnitsByLevel(territoryId, level, 1);
            if (units.size() == 0) {
                return false;
            }
            territories.get(territoryId).getTroop().removeTroop(new unitTroop(playerId,
                    List.of(units.get(0))));
            cSetSpyPos(playerId, territoryId);
            players.get(playerId).reduceTechPoint(20);
            return true;
        }
        return false;
    }

    // set the given territory to be invisible to other players
    public Boolean cacheSetCloak(int playerId, int territoryId) {
        if (this.territories.get(territoryId).getOwner() == playerId &&
            this.players.get(playerId).getCanCloak() &&
            this.players.get(playerId).getTechPoint() >= this.cloakCost) {
                this.cSetCloak(territoryId);
                this.players.get(playerId).reduceTechPoint(this.cloakCost);
            return true;
        }
        return false;
    }

    // move spy to another territory
    public Boolean cacheMoveSpy(int playerId, int to) {
        // get current position of the spy
        int from = players.get(playerId).getSpyPos();
        try {
            // if from and to both belongs to player and they are connected, move spy
            if (world.getTerritories().get(to).getOwner() == playerId &&
                    world.isConnected(from, to, playerId)) {
                players.get(playerId).setSpyPos(to);
                return true;
            }
        } catch (Exception e) {
            ;
        }
        // if from and to adjacent , move spy
        if (world.isNeighbour(from, to)) {
            players.get(playerId).setSpyPos(to);
            return true;
        }

        return false;
    }







}
