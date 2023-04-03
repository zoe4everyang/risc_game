package edu.duke.ece651.risk_game.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class v1WorldMap implements WorldMap{
    private List<Territory> map;
    private int numPlayers;
    
    public v1WorldMap(int numPlayers, List<Territory> map) {
        this.map = map;
        this.numPlayers = numPlayers;
    }

    @Override
    public int getNumTerritories() {
        return map.size();
    }


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

    @Override
    public Boolean isNeighbour(int id1, int id2) {
        return map.get(id1).getDistances().get(id2) == 1;
    }

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

    @Override
    public int getWinner() {
        // return winner
        if (checkEnd()) {
            return map.get(0).getOwner();
        }
        return -1;
    }

    @Override
    public Boolean checkEnd() {
        // check if game ends
        HashSet<Integer> owners = new HashSet<>();
        for (int i = 0; i < map.size(); i++) {
            owners.add(map.get(i).getOwner());
        }
        return owners.size() == 1;
    }

    @Override
    public List<Territory> getTerritories() {
        // return list of territories
        return map;
    }

    @Override
    public void setUnits(List<Integer> placement) {
        // set units for each territory
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i).getUnits() != 0) {
                throw new IllegalArgumentException("Cannot set units for territories that already have units");
            }
            map.get(i).addUnit(placement.get(i));
        }
    }

    @Override
    public void makeAttack(int playerId, int from, int to, int num) {
        // TODO: check if the attack is valid
        map.get(from).removeUnit(num);
        map.get(to).defence(playerId, num);
    }

    @Override
    public void makeMove(int playerId, int from, int to, int num) {
        // TODO: check if the move is valid
        map.get(from).removeUnit(num);
        map.get(to).addUnit(num);
    }
}

