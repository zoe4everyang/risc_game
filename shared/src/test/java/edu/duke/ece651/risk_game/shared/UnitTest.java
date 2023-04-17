package edu.duke.ece651.risk_game.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTest {
    @Test
    public void test_unit() {
        Unit unit = new Unit("test", 0);
        unit.upgrade(1);
        unit.upgrade(2);
        assertEquals(unit.getLevel(), 3);
        assertEquals(unit.getName(), "test");
        assertEquals(unit.getUnitId(), 0);

    }
}
