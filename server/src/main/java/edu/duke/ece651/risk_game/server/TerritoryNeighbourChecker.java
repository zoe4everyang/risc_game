package edu.duke.ece651.risk_game.server;

/**
 * This class is used to check if the territories are neighbours
 */
public class TerritoryNeighbourChecker extends OperationRuleChecker {
    /**
     * Constructor
     * @param nextChecker next checker
     */
    TerritoryNeighbourChecker(OperationRuleChecker nextChecker) {
        super(nextChecker);
    }
    /**
     * Check if the territories are neighbours
     * @param playerId player id
     * @param from from territory
     * @param to to territory
     * @param num number of units
     * @param gameMap game map
     */
    @Override
    protected String checkMyRule(int playerId, int from, int to, int num, WorldMap gameMap) {
        if (!gameMap.isNeighbour(from, to)) {
            return "The territories you are trying to move between are not neighbours";
        }
        return null;
    }
}
