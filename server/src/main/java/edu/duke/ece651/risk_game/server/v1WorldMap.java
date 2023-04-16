package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;

import java.util.*;

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
     * @param Players players of the game
     * @param map map
     * @param unitAvailable number of units available
     */
    public v1WorldMap(List<Player> Players, List<Territory> map, int unitAvailable) {
        this.map = map;
        this.players = Players;
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
     * @param playerId player id
     * @return return the cost of the shortest path from id1 to id2 if such path exists,
     * otherwise return -1
     */
    @Override
    public int shortestPath(int id1, int id2, int playerId) {
        // TODO: test dijkstra
        if (id1 == id2) {
            return 0;
        }
        // use dijkstra to find the shortest path and return the cost
        // initialize the distance
        List<Integer> distance = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            distance.add(Integer.MAX_VALUE);
        }
        distance.set(id1, 0);
        // initialize the visited
        List<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            visited.add(false);
        }
        // initialize the queue
        Queue<Integer> queue = new LinkedList<>();
        queue.add(id1);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            visited.set(cur, true);
            for (int i = 0; i < map.get(cur).getNeighbours().size(); i++) {
                int neighbour = map.get(cur).getNeighbours().get(i);
                if (map.get(neighbour).getOwner() == playerId && !visited.get(neighbour)) {
                    if ( distance.get(neighbour) > distance.get(cur) + map.get(neighbour).getCost()) {
                        distance.set(neighbour, distance.get(cur) + map.get(neighbour).getCost());
                        queue.add(neighbour);
                    }
                }
            }
        }
        if (distance.get(id2) == Integer.MAX_VALUE) {
            return -1;
        }
        return distance.get(id2) - map.get(id2).getCost();
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
        return shortestPath(id1, id2, playerId) > 0;
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

