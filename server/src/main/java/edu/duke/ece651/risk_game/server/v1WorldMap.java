package edu.duke.ece651.risk_game.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class v1WorldMap implements WorldMap{
    private List<Territory> map;
    private int numPlayers;
    private Checker checker;
    
    public v1WorldMap(int numPlayers, List<Territory> map) {
        this.map = map;
        this.numPlayers = numPlayers;
        this.checker = new Checker();
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
            if (placement.get(i) > 0) {
                map.get(i).addUnit(placement.get(i));
            }
        }
    }

    @Override
    public void makeAttack(int playerId, int from, int to, int num) {
        if (!checker.checkAttackTarget(playerId, from, to, num, this)) {
            makeMove(playerId, from, to, num);
            return;
        }
        if (!checker.checkAttack(playerId, from, to, num, this)) {
            return;
        }
        map.get(to).defence(playerId, num);
    }

    @Override
    public void makeMove(int playerId, int from, int to, int num) {
        if (!checker.checkMove(playerId, from, to, num, this)) {
            return;
        }
        map.get(from).removeUnit(num);
        map.get(to).addUnit(num);
    }

    // TODO: check if one id is on the map (used in checker)
    @Override
    public Boolean checkOnMap(int id) {
        for(Territory T : map) {
            if(T.getID() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void resolveAttack(List<Integer> playerIds, 
    List<Integer> fromIds, 
    List<Integer> toIds, 
    List<Integer> unitNums) {
    // get attack units
    for (int i = 0; i < playerIds.size(); i++) {
        if (!checker.checkAttackNumber(playerIds.get(i), fromIds.get(i), toIds.get(i), unitNums.get(i), this)) {
            map.get(i).removeUnit(unitNums.get(i));
        }
    }
    // resolve attack
    for (int i = 0; i < playerIds.size(); i++) {
        makeAttack(playerIds.get(i), fromIds.get(i), toIds.get(i), unitNums.get(i));
    }
    }

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
}

