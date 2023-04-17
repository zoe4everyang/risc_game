package edu.duke.ece651.risk_game.server;

/**
 * This class is used to check if the territory has enough units to move
 */
public class TerritoryUnitNumberChecker extends OperationRuleChecker {
    /**
     * Constructor
     * @param nextChecker next checker
     */
    TerritoryUnitNumberChecker(OperationRuleChecker nextChecker) {
        super(nextChecker);
    }

    /**
     * Check if the territory has enough units to move
     * @param playerId player id
     * @param from from territory
     * @param to to territory
     * @param num number of units
     * @param gameMap game map
     */
    @Override
    protected String checkMyRule(int playerId, int from, int to, int num, WorldMap gameMap) {
        if (gameMap.getTerritories().get(from).getUnits() <= num) {
            return "You must leave at least one unit in the territory you owned";
        }
        return null;
    }
}
