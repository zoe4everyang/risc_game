package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;

import java.util.*;

/**
 * This class is used to represent the world map
 */
public class v1WorldMap implements WorldMap{
    private List<Territory> map;

    private int numPlayers;
    private Checker checker;
    private int unitAvailable;
    /**
     * Constructor
     * @param Players players of the game
     * @param map map
     * @param unitAvailable number of units available
     */
    public v1WorldMap(int numPlayers, List<Territory> map, int unitAvailable) {
        this.map = map;
        this.numPlayers = numPlayers;   // number of players
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
        return shortestPath(id1, id2, playerId) >= 0;
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
                if (map.get(i).getTroopSize() != 0) {
                    throw new IllegalArgumentException("Cannot set units for territories that already have units");
                }
                Troop tmpTroop = new unitTroop(map.get(i).getOwner());
                for (int j = 0; j < placement.get(i); j++) {
                    tmpTroop.addUnit(new Unit("unit"));
                }
                map.get(i).addTroop(tmpTroop);
            }
        }
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

    // attack given territory by troop t
    @Override
    public Boolean makeAttack(int to, Troop t) {
        int originalOwner = map.get(to).getOwner();
        map.get(to).defence(t);
        return map.get(to).getOwner() != originalOwner;
    }

    // move troop from one territory to another
    // return false if cannot remove troop from from
    @Override
    public Boolean makeMove(int from, int to, Troop t) {
        try {
            map.get(from).removeTroop(t);
            map.get(to).addTroop(t);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // upgrade unit by given amount
    @Override
    public void upgradeUnit(int territoryId, int UnitId, int amount) {
        map.get(territoryId).upgradeUnit(UnitId, amount);

    }



    @Override
    public int getUnitAvailable() {
        return unitAvailable;
    }
}

