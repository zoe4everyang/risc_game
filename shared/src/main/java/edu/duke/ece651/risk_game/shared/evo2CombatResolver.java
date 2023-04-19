package edu.duke.ece651.risk_game.shared;

import java.util.HashMap;

public class evo2CombatResolver implements CombatResolver {
    private final HashMap<Integer, Integer> levelBonus;
    private Boolean isRandom = true;
    public evo2CombatResolver() {
        this.levelBonus = new HashMap<>();
        this.levelBonus.put(0, 0);
        this.levelBonus.put(1, 50);
        this.levelBonus.put(2, 3);
        this.levelBonus.put(3, 5);
        this.levelBonus.put(4, 8);
        this.levelBonus.put(5, 11);
        this.levelBonus.put(6, 15);
    }

        @Override
        public Troop resolveCombat(Troop attackTroop, Troop defendTroop) {
            attackTroop.getUnits().sort((u1, u2) -> u2.getLevel() - u1.getLevel());
            defendTroop.getUnits().sort((u1, u2) -> u1.getLevel() - u2.getLevel());
            // pair two list of units by index, and resolve combat
            Boolean attacker = true;
            while (attackTroop.getUnits().size() > 0 && defendTroop.getUnits().size() > 0) {
                Unit attackUnit;
                Unit defendUnit;
                if (attacker) {
                    attackUnit = attackTroop.getUnits().get(0);
                    defendUnit = defendTroop.getUnits().get(0);
                } else {
                    attackUnit = attackTroop.getUnits().get(attackTroop.getUnits().size() - 1);
                    defendUnit = defendTroop.getUnits().get(defendTroop.getUnits().size() - 1);
                }
                if (resolveCombat(attackUnit, defendUnit)) {
                    defendTroop.deleteUnit(defendUnit.getUnitId());
                } else {
                    attackTroop.deleteUnit(attackUnit.getUnitId());
                }
                attacker = !attacker;
            }
            return attackTroop.getUnits().size() > 0 ? attackTroop : defendTroop;
        }
        // return true if attack wins, false if defend wins
        protected Boolean resolveCombat(Unit attackUnit, Unit defendUnit) {
            int attackBonus = this.levelBonus.get(attackUnit.getLevel());
            int defendBonus = this.levelBonus.get(defendUnit.getLevel());
            int attackRoll, defendRoll;
            if (isRandom) {
                attackRoll = (int) (Math.random() * 20) + attackBonus;
                defendRoll = (int) (Math.random() * 20) + defendBonus;
            } else {
                attackRoll = 1 + attackBonus;
                defendRoll = 1 + defendBonus;
            }
            if (attackRoll > defendRoll) {
                return true;
            } else {
                return false;
            }
        }


        @Override
        public int resolveCombat(int attackNum, int defendNum) {
            return 0;
        }

    @Override
    public void setSeed(int seed) {
        return ;
    }

    @Override
        public void setRandom(Boolean isRandom) {
            this.isRandom = isRandom;
            return ;
        }
}
