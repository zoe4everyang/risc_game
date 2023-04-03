package edu.duke.ece651.risk_game.server;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class v1MapFactoryTest {
    @Test
    public void test_make2PlayerMap() {
        MapFactory mapFactory = new v1MapFactory();
        List<Territory> territories = mapFactory.make2PlayerMap();
        assertEquals(territories.size(), 6);
        assertEquals(territories.get(0).getOwner(), 0);
        assertEquals(territories.get(1).getOwner(), 0);
        assertEquals(territories.get(2).getOwner(), 0);
        assertEquals(territories.get(3).getOwner(), 1);
        assertEquals(territories.get(4).getOwner(), 1);
        assertEquals(territories.get(5).getOwner(), 1);
        assertEquals(territories.get(0).getUnits(), 0);
        assertEquals(territories.get(1).getUnits(), 0);
        assertEquals(territories.get(2).getUnits(), 0);
        assertEquals(territories.get(3).getUnits(), 0);
        assertEquals(territories.get(4).getUnits(), 0);
        assertEquals(territories.get(5).getUnits(), 0);
    }
}
