//package edu.duke.ece651.risk_game.server;
//
///**
// * This class is used to check the validity of the operation
// * @author
// * @version 1.0
// */
//public class Checker {
//    /**
//     * This method is used to check the validity of the operation
//     * @param playerId the id of the player who is trying to make the operation
//     * @param from the id of the territory the player is trying to move from
//     * @param to the id of the territory the player is trying to move to
//     * @param num the number of units the player is trying to move
//     * @param map the world map
//     * @return true if the operation is valid, false otherwise
//     */
//    public Boolean checkMove(int playerId, int from, int to, int num, WorldMap map) {
//        if (map.getTerritories().get(from).getOwner() != playerId || map.getTerritories().get(to).getOwner() != playerId) {
//
//            // throw new IllegalArgumentException("you are moving from/to a territory not belong to you!");
//            return false;
//        }
//        if (!map.isConnected(from, to, playerId)) {
//            // throw new IllegalArgumentException("you can not moving to a unconnect territory!");
//            return false;
//        }
//        if (num > map.getTerritories().get(from).getUnits() - 1) {
//            // throw new IllegalArgumentException("you do not have enough units to make the movement!");
//
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * This method is used to check the validity of the operation
//     * @param playerId the id of the player who is trying to make the operation
//     * @param from the id of the territory the player is trying to move from
//     * @param to the id of the territory the player is trying to move to
//     * @param num the number of units the player is trying to move
//     * @param map the world map
//     * @return true if the operation is valid, false otherwise
//     */
//    public Boolean checkAttackTarget(int playerId, int from , int to, int num, WorldMap map) {
//        if (map.getTerritories().get(from).getOwner() != playerId || map.getTerritories().get(to).getOwner() == playerId) {
//
//            // throw new IllegalArgumentException("you can not attack yourself!!");
//            return false;
//        }
//        return true;
//    }
//    /**
//     * This method is used to check the validity of the operation
//     * @param playerId the id of the player who is trying to make the operation
//     * @param from the id of the territory the player is trying to move from
//     * @param to the id of the territory the player is trying to move to
//     * @param num the number of units the player is trying to move
//     * @param map the world map
//     * @return true if the operation is valid, false otherwise
//     */
//    public Boolean checkAttackNumber(int playerId, int from, int to, int num, WorldMap map) {
//        if (num >= map.getTerritories().get(from).getUnits()) {
//            // throw new IllegalArgumentException("you do not have enough units to attack!");
//            return false;
//        }
//        return true;
//    }
//    /**
//     * This method is used to check the validity of the operation
//     * @param playerId the id of the player who is trying to make the operation
//     * @param from the id of the territory the player is trying to move from
//     * @param to the id of the territory the player is trying to move to
//     * @param num the number of units the player is trying to move
//     * @param map the world map
//     * @return true if the operation is valid, false otherwise
//     */
//    public Boolean checkNeighbour(int playerId, int from, int to, int num, WorldMap map) {
//        if (!map.isNeighbour(from, to)) {
//            // throw new IllegalArgumentException("you can only attack the connected territory!");
//
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * This method is used to check the validity of the operation
//     * @param playerId the id of the player who is trying to make the operation
//     * @param from the id of the territory the player is trying to move from
//     * @param to the id of the territory the player is trying to move to
//     * @param num the number of units the player is trying to move
//     * @param map the world map
//     * @return true if the operation is valid, false otherwise
//     */
//    public Boolean checkUsable(int playerId, int from, int to, int num, WorldMap map) {
//       if(map.checkOnMap(to) == false && map.checkOnMap(from) == false) {
//            // throw new IllegalArgumentException("you are trying to move from/to a territory which does not exist!");
//
//            return false;
//       }
//       return true;
//    }
//}
