package edu.duke.ece651.risk_game.shared;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TroopTest {
    @Test
    public void test_troop() {
        Troop troop = new unitTroop(0);
        assertEquals(0, troop.getOwner());
        troop.addUnit(new Unit("test", 0));
        troop.addUnit(new Unit("test2", 888));
        troop.addUnits(List.of(new Unit("test3", 2), new Unit("test4", 3)));
        assertEquals(troop.getUnits().size(), 4);
        assertEquals(troop.getUnits().get(0).getName(), "test");
        assertEquals(troop.getUnits().get(1).getName(), "test2");
        assertEquals(troop.getUnits().get(2).getName(), "test3");
        assertEquals(troop.getUnits().get(3).getName(), "test4");
        troop.deleteUnit(888);
        assertEquals(troop.getUnits().size(), 3);
        assertEquals(troop.getUnits().get(0).getName(), "test");
        assertEquals(troop.getUnits().get(1).getName(), "test3");
        assertEquals(troop.getUnits().get(2).getName(), "test4");

        troop.setOwner(1);
        assertEquals(1, troop.getOwner());
        assertEquals(troop.getUnits().get(0).getLevel(), 0);
        troop.upgrade(2, 5);
        assertEquals(troop.getUnits().get(2).getLevel(), 5);
    }
}
