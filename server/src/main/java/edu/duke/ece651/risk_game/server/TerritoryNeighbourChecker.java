package edu.duke.ece651.risk_game.server;

public class TerritoryNeighbourChecker extends OperationRuleChecker {
    TerritoryNeighbourChecker(OperationRuleChecker nextChecker) {
        super(nextChecker);
    }
    @Override
    protected String checkMyRule(int playerId, int from, int to, int num, WorldMap gameMap) {
        if (!gameMap.isNeighbour(from, to)) {
            return "The territories you are trying to move between are not neighbours";
        }
        return null;
    }
}
