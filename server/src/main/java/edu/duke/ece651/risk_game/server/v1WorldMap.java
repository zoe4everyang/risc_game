package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * This class is used to represent the world map
 */
public class v1WorldMap implements WorldMap{
    private List<Territory> map;

    private List<Player> players;
    private Checker checker;
    private int unitAvailable;
    /**
     * Constructor
     * @param numPlayers number of players
     * @param map map
     * @param unitAvailable number of units available
     */
    public v1WorldMap(int numPlayers, List<Territory> map, int unitAvailable) {
        this.map = map;
        this.numPlayers = numPlayers;
        this.checker = new Checker();
        this.unitAvailable = unitAvailable;
    }


    /**
     * Get the map
     * @return map
     */
    @Override
    public int getNumTerritories() {
        return map.size();
    }

    /**
     * Get the map
     * @param id1 id1
     * @param id2 id2
     * @param visited visited
     * @param playerId player id
     * @return map
     */
    private Boolean dfs(int id1, int id2, List<Boolean> visited, int playerId) {
        // if id1 is visited, return false
        if (id1 == id2) {
            return true;
        }
        if (visited.get(id1)) {
            return false;
        }
        // if id1 is not visited, mark it as visited
        visited.set(id1, true);
        // if id1 is not connected to id2, dfs on all its neighbours
        for (int i = 0; i < map.size(); i++) {
            if (isNeighbour(id1, i) && !visited.get(i) && map.get(i).getOwner() == playerId) {
                if (dfs(i, id2, visited, playerId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the map
     * @param id1 id1
     * @param id2 id2
     * @param playerId player id
     * @return map
     */
    @Override
    public Boolean isConnected(int id1, int id2, int playerId) {
        // dfs to check if id1 is connected to id2
        if (map.get(id1).getOwner() != playerId || map.get(id2).getOwner() != playerId) {
            throw new IllegalArgumentException("PlayerId is not valid");
        }
        // if id1 == id2, return true
        if (id1 == id2) {
            return true;
        }
        // if id1 is not connected to id2, return false
        
        List<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            visited.add(false);
        }
        return dfs(id1, id2, visited, playerId);
    }

    /**
     * Get the map
     * @param id1 id1
     * @param id2 id2
     * @return map
     */
    @Override
    public Boolean isNeighbour(int id1, int id2) {
        return map.get(id1).getDistances().get(id2) == 1;
    }

    /**
     * Get the map
     */
    @Override
    public List<Integer> getLosers() {
        // return list of losers
        ArrayList<Boolean> isLosed = new ArrayList<Boolean>();
        for (int i = 0; i < numPlayers; i++) {
            isLosed.add(true);
        }
        for (int i = 0; i < map.size(); i++) {
            isLosed.set(map.get(i).getOwner(), false);
        }
        ArrayList<Integer> losers = new ArrayList<Integer>();
        for (int i = 0; i < numPlayers; i++) {
            if (isLosed.get(i)) {
                losers.add(i);
            }
        }
        return losers;
    }

    /**
     * Get the map
     * @return map
     */
    @Override
    public int getWinner() {
        // return winner
        if (checkEnd()) {
            return map.get(0).getOwner();
        }
        return -1;
    }

    /**
     * Get the map
     * @return map
     */
    @Override
    public Boolean checkEnd() {
        // check if game ends
        HashSet<Integer> owners = new HashSet<>();
        for (int i = 0; i < map.size(); i++) {
            owners.add(map.get(i).getOwner());
        }
        return owners.size() == 1;
    }

    /**
     * Get the map
     * @return map
     */
    @Override
    public List<Territory> getTerritories() {
        // return list of territories
        return map;
    }

    /**
     * Get the map
     * @param placement placement
     * @return map
     */
    @Override
    public void setUnits(List<Integer> placement) {
        // set units for each territory
        for (int i = 0; i < map.size(); i++) {
            if (placement.get(i) > 0) {
                if (map.get(i).getUnits() != 0) {
                    throw new IllegalArgumentException("Cannot set units for territories that already have units");
                }
                map.get(i).addUnit(placement.get(i));
            }
        }
    }

    /**
     * Get the map
     * @param playerId player id
     * @param from from
     * @param to to
     * @param num num
     * @return map
     */
    @Override
    public void makeAttack(int playerId, int from, int to, int num) {
        // if the target it already belongs to the player, move units to the target
        if (!checker.checkAttackTarget(playerId, from, to, num, this)) {
            map.get(to).addUnit(num);
            return;
        }


        map.get(to).defence(playerId, num);
    }

    /**
     * Get the map
     * @param playerId player id
     * @param from from
     * @param to to
     * @param num num
     * @return map
     */
    @Override
    public void makeMove(int playerId, int from, int to, int num) {
        if (!checker.checkMove(playerId, from, to, num, this)) {
            return;
        }
        map.get(from).removeUnit(num);
        map.get(to).addUnit(num);
    }

    /**
     * Get the map
     * @param id id
     * @return map
     */
    @Override
    public Boolean checkOnMap(int id){
        for(Territory T : map){
            if(T.getID() == id){
                return true;
            }
        }
        return false;
    }

    /**
     * Get the map
     * @param playerIds
     * @param fromIds
     * @param toIds
     * @param unitNums
     */
    @Override
    public void resolveAttack(List<Integer> playerIds, 
    List<Integer> fromIds, 
    List<Integer> toIds, 
    List<Integer> unitNums) {
        // get attack units
        HashSet<Integer> verified = new HashSet<>();
        for (int i = 0; i < playerIds.size(); i++) {
            if (checker.checkAttackTarget(playerIds.get(i), fromIds.get(i), toIds.get(i), unitNums.get(i), this) &&
            checker.checkAttackNumber(playerIds.get(i), fromIds.get(i), toIds.get(i), unitNums.get(i), this) &&
            checker.checkNeighbour(playerIds.get(i), fromIds.get(i), toIds.get(i), unitNums.get(i), this)) {
                map.get(fromIds.get(i)).removeUnit(unitNums.get(i));
                verified.add(i);
            }
        }
        // resolve attack
        for (int i = 0; i < playerIds.size(); i++) {
            if (verified.contains(i)) {
                makeAttack(playerIds.get(i), fromIds.get(i), toIds.get(i), unitNums.get(i));
            }
        }
    }

    /**
     * Get the map
     * @param playerIds
     * @param fromIds
     * @param toIds
     * @param unitNums
     */
    @Override
    public void resolveMove(List<Integer> playerIds, 
            List<Integer> fromIds, 
            List<Integer> toIds, 
            List<Integer> unitNums
            ) {
        // resolve move
        for (int i = 0; i < playerIds.size(); i++) {
            makeMove(playerIds.get(i), fromIds.get(i), toIds.get(i), unitNums.get(i));
        }
    }
    @Override
    public int getUnitAvailable() {
        return unitAvailable;
    }
}

