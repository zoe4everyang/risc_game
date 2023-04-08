package edu.duke.ece651.risk_game.server;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import edu.duke.ece651.risk_game.shared.*;

public class v1WorldMapTest {
    @Test
    public void test_isNeighbhour() {
        MapFactory mapFactory = new v1MapFactory();
        WorldMap map = mapFactory.make2PlayerMap();
        assertTrue(map.isNeighbour(0, 1));
        assertFalse(map.isNeighbour(0, 5));
        assertTrue(map.isNeighbour(0, 2));
        assertTrue(map.isNeighbour(0, 2));
        assertTrue(map.isNeighbour(2, 4));
        assertFalse(map.isNeighbour(3, 4));
        assertTrue(map.isNeighbour(4, 5));
        assertTrue(map.isNeighbour(3, 5));
    }

    @Test
    public void test_isConnected() {
        MapFactory mapFactory = new v1MapFactory();
        WorldMap map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(10, 10, 10, 100, 100, 100));
        assertTrue(map.isConnected(0, 1, 0));
        assertTrue(map.isConnected(0, 2, 0));
        assertTrue(map.isConnected(1, 2, 0));
        assertThrows(IllegalArgumentException.class, () -> map.isConnected(0, 3, 0));
        assertThrows(IllegalArgumentException.class, () -> map.isConnected(0, 4, 0));
        assertTrue(map.isConnected(3, 4, 1));
        assertTrue(map.isConnected(3, 5, 1));
        assertTrue(map.isConnected(4, 5, 1));
        map.makeAttack(1, 3, 1, 99);
        assertTrue(map.isConnected(4, 1, 1));
        assertTrue(map.isConnected(1, 5, 1));
        assertTrue(map.isConnected(1, 3, 1));
        assertTrue(map.isConnected(0, 2, 0));
        assertTrue(map.isConnected(2, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> map.isConnected(2, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> map.isConnected(2, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> map.isConnected(2, 4, 1));
    }

    // check List/winner
    @Test
    public void test_checkEnd() {
        MapFactory mapFactory = new v1MapFactory();
        WorldMap map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(10, 10, 10, 10000, 10000, 10000));
        map.makeAttack(1, 3, 1, 100);
        map.makeAttack(1, 4, 2, 100);
        map.makeAttack(1, 1, 0, 50);
        assertEquals(map.getWinner(),1);
        assertTrue(map.checkEnd());
    }

}
