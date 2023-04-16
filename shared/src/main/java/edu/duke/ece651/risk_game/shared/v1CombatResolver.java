package edu.duke.ece651.risk_game.shared;

import java.util.Random;

public class v1CombatResolver implements CombatResolver {
    
    private int seed;

    private Random generator;

    public v1CombatResolver() {
        seed = (int) Math.random() * 5000;
        generator = new Random(seed);
    }

    public int resolveCombat(int attackNum, int defendNum) {
        // return the number of units that survive, positive indicate attacker wins, negative indicate defender wins
        // the absolute value of return value indicate the number of units that survive
        while (attackNum > 0 && defendNum > 0) {
            // roll two 20-sided dice for attacker and defender
            // the one with lower number loses one unit
            int attackRoll = (int) (generator.nextDouble() * 20) + 1;
            int defendRoll = (int) (generator.nextDouble() * 20) + 1;
            if (attackRoll > defendRoll) {
                defendNum--;
            } else {
                attackNum--;
            }
        }
        return attackNum - defendNum;
    }

    @Override
    public Troop resolveCombat(Troop attackTroop, Troop defendTroop) {
        return null;
    }

    public void setSeed(int seed) {
        // set the seed for random number generator
        this.seed = seed;
        // set seed for Math.random
        generator = new Random(seed);
    }

    @Override
    public void setRandom(Boolean isRandom) {

    }

}
