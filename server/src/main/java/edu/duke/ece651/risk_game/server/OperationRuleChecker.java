package edu.duke.ece651.risk_game.server;

import java.util.List;

/**
 * This interface is used to make different maps for different number of players
 */
public abstract class OperationRuleChecker {
    private final OperationRuleChecker nextChecker;
    OperationRuleChecker(OperationRuleChecker nextChecker) {
        this.nextChecker = nextChecker;
    }

    /**
     * Check if the game is over
     * @param playerId player id
     * @param from from territory
     * @param to to territory
     * @param num number of units
     * @param gameMap game map
     */
    protected abstract String checkMyRule(int playerId, int from, int to, int num, WorldMap gameMap);

    /**
     * Check if the game is over
     * @param playerId player id
     * @param from from territory
     * @param to to territory
     * @param num number of units
     * @param gameMap game map
     */
    public Boolean checkRule(int playerId, int from, int to, int num, WorldMap gameMap) {
        String result = checkMyRule(playerId, from, to, num, gameMap);
        if (result != null) {
            System.out.println(result);
            return false;
        }
        if (nextChecker != null) {
            return nextChecker.checkRule(playerId, from, to, num, gameMap);
        }
        return true;
    }
}
