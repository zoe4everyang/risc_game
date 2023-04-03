package edu.duke.ece651.risk_game.server;

public class TerritoryUnitNumberChecker extends OperationRuleChecker {
    TerritoryUnitNumberChecker(OperationRuleChecker nextChecker) {
        super(nextChecker);
    }

    @Override
    protected String checkMyRule(int playerId, int from, int to, int num, WorldMap gameMap) {
        if (gameMap.getTerritories().get(from).getUnits() <= num) {
            return "You must leave at least one unit in the territory you owned";
        }
        return null;
    }
}
