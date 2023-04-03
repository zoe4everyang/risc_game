package edu.duke.ece651.risk_game.server;

import java.util.List;

public abstract class OperationRuleChecker {
    private final OperationRuleChecker nextChecker;
    OperationRuleChecker(OperationRuleChecker nextChecker) {
        this.nextChecker = nextChecker;
    }

    protected abstract String checkMyRule(int playerId, int from, int to, int num, WorldMap gameMap);

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
