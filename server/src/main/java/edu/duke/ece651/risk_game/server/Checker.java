package edu.duke.ece651.risk_game.server;

public class Checker {
    public Boolean checkMove(int playerId, int from, int to, int num, WorldMap map) {
        if (map.getTerritories().get(from).getOwner() != playerId || map.getTerritories().get(to).getOwner() != playerId) {
            return false;
        }
        if (!map.isConnected(from, to, playerId)) {
            return false;
        }
        if (num > map.getTerritories().get(from).getUnits() - 1) {
            return false;
        }
        return true;   
    }
    public Boolean checkAttackTarget(int playerId, int from , int to, int num, WorldMap map) {
        if (map.getTerritories().get(from).getOwner() != playerId || map.getTerritories().get(to).getOwner() == playerId) {
            return false;
        }
        return true;
    }
    public Boolean checkAttack(int playerId, int from, int to, int num, WorldMap map) {
        if (!checkAttackTarget(playerId, from, to, num, map)) {
            return false;
        }
        if (!map.isNeighbour(from, to)) {
            return false;
        }
        if (num > map.getTerritories().get(from).getUnits() - 1) {
            return false;
        }
        return true;
    }
}
