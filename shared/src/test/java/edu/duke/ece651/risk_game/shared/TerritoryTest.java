package edu.duke.ece651.risk_game.shared;

import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TerritoryTest {
    @Test
    public void test_terittoryDefence() {
        CombatResolver cr = new evo2CombatResolver();
        cr.setRandom(false);
        Territory territory = new v1Territory(0, "t1",
                0, List.of(0, 1),
                30, 5, 5, cr);

        territory.addTroop(new unitTroop(0, List.of(new Unit("test", 3, 0))));


        Troop attackTroop = new unitTroop(1, List.of(new Unit("t", 2, 3)));


        assertEquals(0, territory.getOwner());
        attackTroop.upgrade(3, 2);
        territory.defence(attackTroop);
        assertEquals(1, territory.getOwner());
    }
}
