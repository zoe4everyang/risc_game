package edu.duke.ece651.risk_game.server;


public class TerritoryIsConnectedChecker extends OperationRuleChecker{
    public TerritoryIsConnectedChecker(OperationRuleChecker nextChecker) {
        super(nextChecker);
    }

    @Override
    protected String checkMyRule(int playerId, int from, int to, int num, WorldMap gameMap) {
        if (!gameMap.isConnected(from, to, playerId)) {
            return "The territories you are trying to move between are not connected";
        }
        return null;
    }
}
