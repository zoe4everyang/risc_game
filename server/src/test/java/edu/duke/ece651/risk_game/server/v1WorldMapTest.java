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
    public void test_shortestPath() {
        MapFactory mapFactory = new v1MapFactory();
        WorldMap map = mapFactory.make2PlayerMap();
        assertEquals(0, map.shortestPath(0, 1, 0));
        assertEquals(0, map.shortestPath(0, 2, 0));
        assertEquals(20, map.shortestPath(1, 2, 0));
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

    }

    // check end/winner
    @Test
    public void test_checkEnd() {
        MapFactory mapFactory = new v1MapFactory();
        WorldMap map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(10, 10, 10, 10000, 10000, 10000));
        map.makeAttack(0, createTroop(1, 1000));

        map.makeAttack(1, createTroop(1, 1000));
        map.makeAttack(2, createTroop(1, 1000));
        assertEquals(1, map.getTerritories().get(0).getOwner());
        assertEquals(1, map.getTerritories().get(1).getOwner());
        assertEquals(1, map.getTerritories().get(2).getOwner());
        assertEquals(map.getWinner(),1);
        assertTrue(map.checkEnd());
    }
    public Troop createTroop(int owner, int num) {
        Troop attackTroop = new unitTroop(owner);
        for (int i = 0; i < num; ++i) {
            attackTroop.addUnit(new Unit("hello"));
        }
        return attackTroop;
    }
    @Test
    public void test_attack() {
        MapFactory mapFactory = new v1MapFactory();
        WorldMap map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(10, 10, 10, 100, 100, 100));
        Troop attackTroop = createTroop(1, 50);
        map.makeAttack(0, attackTroop);
        attackTroop = createTroop(1, 50);
        map.makeAttack(1, attackTroop);
        attackTroop = createTroop(1, 50);
        map.makeAttack(2, attackTroop);

        assertEquals(map.getTerritories().get(0).getOwner(), 1);
        assertEquals(map.getTerritories().get(1).getOwner(), 1);
        assertEquals(map.getTerritories().get(2).getOwner(), 1);

        attackTroop = createTroop(0, 50);
        map.makeAttack(5, attackTroop);
        attackTroop = createTroop(0, 50);
        map.makeAttack(5, attackTroop);
        attackTroop = createTroop(0, 50);
        map.makeAttack(5, attackTroop);

        assertEquals(map.getTerritories().get(3).getOwner(), 1);
        assertEquals(map.getTerritories().get(4).getOwner(), 1);
        assertEquals(map.getTerritories().get(5).getOwner(), 0);
    }

    @Test
    public void test_move() {
        // test normal move
        // test move from and to territories belong to different player // should do nothing
        MapFactory mapFactory = new v1MapFactory();
        WorldMap map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(10, 10, 10, 100, 100, 100));
        assertEquals(10, map.getTerritories().get(1).getTroopSize());
        assertEquals(10, map.getTerritories().get(2).getTroopSize());
        Troop t = new unitTroop(0);
        t.addUnit(new Unit("hello", 0, 11));
        map.makeMove(1, 2, t);
        assertEquals(9, map.getTerritories().get(1).getTroopSize());
        assertEquals(11, map.getTerritories().get(2).getTroopSize());
    }

    @Test
    public void test_upgrade() {
        MapFactory mapFactory = new v1MapFactory();
        WorldMap map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(10, 10, 10, 100, 100, 100));
        assertEquals(0, map.getTerritories().get(0).getTroop().getUnits().get(0).getLevel());
        map.upgradeUnit(0, 0, 3);
        assertEquals(3, map.getTerritories().get(0).getTroop().getUnits().get(0).getLevel());
        map.upgradeUnit(1, 10, 3);
        assertEquals(3, map.getTerritories().get(1).getTroop().getUnits().get(0).getLevel());
    }

}
