package edu.duke.ece651.risk_game.server;
public interface CombatResolver {
    // return the number of units that survive, positive indicate attacker wins, negative indicate defender wins
    // the absolute value of return value indicate the number of units that survive
    public int resolveCombat(int attackNum, int defendNum);
    // set the seed for random number generator
    public void setSeed(int seed);
}
