package edu.duke.ece651.risk_game.server;

public class TerritoryBelongsToChecker extends OperationRuleChecker {
    public TerritoryBelongsToChecker(OperationRuleChecker nextChecker) {
        super(nextChecker);
    }

    @Override
    protected String checkMyRule(int playerId, int from, int to, int num, WorldMap gameMap) {
        if (gameMap.getTerritories().get(from).getOwner() != playerId) {
            return "You don't own the territory you are trying to move from";
        }
        return null;
    }

   
}
