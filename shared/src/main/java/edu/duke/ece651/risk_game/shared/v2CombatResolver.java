package edu.duke.ece651.risk_game.shared;

public class v2CombatResolver implements CombatResolver{
    @Override
    public int resolveCombat(int attackNum, int defendNum) {
        return attackNum - defendNum;
    }

    @Override
    public void setSeed(int seed) {
        return ;
    }
}
