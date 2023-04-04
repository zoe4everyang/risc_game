package edu.duke.ece651.risk_game.server;

public class Checker {
    public Boolean checkMove(int playerId, int from, int to, int num, WorldMap map) {
        if (map.getTerritories().get(from).getOwner() != playerId || map.getTerritories().get(to).getOwner() != playerId) {
            //throw new IllegalArgumentException("you are moving from/to a territory not belong to you!");
            return false;
        }
        if (!map.isConnected(from, to, playerId)) {
            //throw new IllegalArgumentException("you can not moving to a unconnect territory!");
            return false;
        }
        if (num > map.getTerritories().get(from).getUnits() - 1) {
            //throw new IllegalArgumentException("you do not have enough units to make the movement!");
            return false;
        }
        return true;   
    }

    public Boolean checkAttackTarget(int playerId, int from , int to, int num, WorldMap map) {
        if (map.getTerritories().get(from).getOwner() != playerId || map.getTerritories().get(to).getOwner() == playerId) {
            //throw new IllegalArgumentException("you can not attack yourself!!");
            return false;
        }
        return true;
    }

    public Boolean checkAttack(int playerId, int from, int to, int num, WorldMap map) {
        if (!checkAttackTarget(playerId, from, to, num, map)) {
            return false;
        }
        if (!map.isNeighbour(from, to)) {
            //throw new IllegalArgumentException("you can only attack the connected territory!");
            return false;
        }
        if (num > map.getTerritories().get(from).getUnits() - 1) {
            //throw new IllegalArgumentException("you do not have enough units to attack!");
            return false;
        }
        return true;
    }

    // TODO: check the new check method
    public Boolean checkUsable(int playerId, int from, int to, int num, WorldMap map) {
       if(map.checkOnMap(to) == false || map.checkOnMap(from) == false) {
            //throw new IllegalArgumentException("you are trying to move from/to a territory which does not exist!");
            return false;
       }
    }
}
