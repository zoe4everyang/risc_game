package edu.duke.ece651.risk_game.shared;

/*
 * This interface is used to resolve the combat between two players.
 * The attacker and defender will roll the dice and compare the result.
 * The one with lower number loses one unit.
 * The attacker will roll two 20-sided dice and the defender will roll one 20-sided dice.
 */
public interface CombatResolver {

    // Return the winner of the combat
    public Troop resolveCombat(Troop attackTroop, Troop defendTroop);
    public int resolveCombat(int attackNum, int defendNum);
    // set the seed for random number generator
    public void setSeed(int seed);

    void setRandom(Boolean isRandom);
}
