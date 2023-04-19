//package edu.duke.ece651.risk_game.server;
//
///**
// * This class is used to check if the territory belongs to the player
// */
//public class TerritoryBelongsToChecker extends OperationRuleChecker {
//    /**
//     * Constructor
//     * @param nextChecker next checker
//     */
//    public TerritoryBelongsToChecker(OperationRuleChecker nextChecker) {
//        super(nextChecker);
//    }
//
//    /**
//     * Check if the territory belongs to the player
//     * @param playerId player id
//     * @param from from territory
//     * @param to to territory
//     * @param num number of units
//     * @param gameMap game map
//     */
//    @Override
//    protected String checkMyRule(int playerId, int from, int to, int num, WorldMap gameMap) {
//        if (gameMap.getTerritories().get(from).getOwner() != playerId) {
//            return "You don't own the territory you are trying to move from";
//        }
//        return null;
//    }
//
//
//}
