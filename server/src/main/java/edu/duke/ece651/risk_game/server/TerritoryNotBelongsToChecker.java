package edu.duke.ece651.risk_game.server;


public class TerritoryNotBelongsToChecker extends OperationRuleChecker {
    public TerritoryNotBelongsToChecker(OperationRuleChecker nextChecker) {
        super(nextChecker);
    }

    @Override
    protected String checkMyRule(int playerId, int from, int to, int num, WorldMap gameMap) {
        if (gameMap.getTerritories().get(from).getOwner() == playerId) {
            return "You own the territory you are trying to move from";
        }
        return null;
    }
}
    
