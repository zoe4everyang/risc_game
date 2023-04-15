package edu.duke.ece651.risk_game.shared;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class evo2CombatResolverTest {
    @Test
    public void testCombatResolver() {
        CombatResolver combatResolver = new evo2CombatResolver();
        combatResolver.setRandom(false);
        combatResolver.setSeed(0);
        Troop troop1 = new unitTroop(0);
        troop1.addUnit(new Unit("test", 2, 0));
        troop1.addUnit(new Unit("test2", 3, 888));
        troop1.addUnits(List.of(new Unit("test3", 5, 2), new Unit("test4", 0,3)));
        Troop troop2 = new unitTroop(1);
        troop2.addUnit(new Unit("test5", 6, 4));
        troop2.addUnit(new Unit("test6", 3, 5));
        Troop winner = combatResolver.resolveCombat(troop1, troop2);
        assertEquals(1, winner.getOwner());
        assertEquals(1, winner.getUnits().size());
        assertEquals(6, winner.getUnits().get(0).getLevel());
    }

    @Test
    public void testCombatResolverUnit() {
        evo2CombatResolver combatResolver = new evo2CombatResolver();
        combatResolver.setRandom(false);
        combatResolver.setSeed(0);
        Unit unit1 = new Unit("test", 2, 0);
        Unit unit2 = new Unit("test2", 1, 888);
        assertTrue(combatResolver.resolveCombat(unit1, unit2));
        assertFalse(combatResolver.resolveCombat(unit2, unit1));

    }
}
