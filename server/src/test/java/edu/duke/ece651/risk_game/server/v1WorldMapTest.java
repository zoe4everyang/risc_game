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

    @Test
    public void test_attack() {
        MapFactory mapFactory = new v1MapFactory();
        WorldMap map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(-1, -1, -1, 500, 500, 500));
        map.setUnits(List.of(20, 20, 20, -1, -1, -1));
        // test normal attack
        map.resolveAttack(List.of(1, 1), List.of(3, 3), List.of(1, 1), List.of(5, 1));
        assertTrue(map.getTerritories().get(1).getUnits() < 20);
        assertTrue(map.getTerritories().get(3).getUnits() == 494);
        // test attack with 0 unit // should do nothing
        map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(-1, -1, -1, 500, 500, 500));
        map.setUnits(List.of(20, 20, 20, -1, -1, -1));
        map.resolveAttack(List.of(1, 1), List.of(3, 3), List.of(1, 1), List.of(0, 0));
        assertTrue(map.getTerritories().get(1).getUnits() == 20);
        assertTrue(map.getTerritories().get(3).getUnits() == 500);

        // test attack with non-neighbhour // should do nothing
        map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(-1, -1, -1, 500, 500, 500));
        map.setUnits(List.of(20, 20, 20, -1, -1, -1));
        map.resolveAttack(List.of(1, 1), List.of(3, 3), List.of(1, 0), List.of(5, 30));
        assertTrue(map.getTerritories().get(0).getUnits() == 20);
        assertTrue(map.getTerritories().get(1).getUnits() < 20);
        assertTrue(map.getTerritories().get(3).getUnits() == 495);

        // test attack same territory as from // should do nothing
        map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(-1, -1, -1, 500, 500, 500));
        map.setUnits(List.of(20, 20, 20, -1, -1, -1));
        map.resolveAttack(List.of(1, 1), List.of(3, 3), List.of(3, 3), List.of(5, 30));
        assertTrue(map.getTerritories().get(3).getUnits() == 500);

        // test attack territory already been attacked by the same player // should move units to the territory
        map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(-1, -1, -1, 500, 500, 500));
        map.setUnits(List.of(20, 20, 20, -1, -1, -1));
        map.resolveAttack(List.of(1, 1), List.of(3, 3), List.of(1, 1), List.of(50, 200));
        assertTrue(map.getTerritories().get(3).getUnits() == 250);
        assertEquals(1, map.getTerritories().get(1).getOwner());
        assertTrue(map.getTerritories().get(1).getUnits() > 200);

        // test attack territory already been attacked by the different player // should combat with the new player
        map = mapFactory.make3PlayerMap();
        map.setUnits(List.of(-1, -1, -1, 500, 500, 500));
        map.setUnits(List.of(20, 20, 500, -1, -1, -1));
        map.resolveAttack(List.of(1), List.of(2), List.of(0), List.of(300));
        assertTrue(map.getTerritories().get(2).getUnits() == 200);
        assertEquals(1, map.getTerritories().get(0).getOwner());
        int originalZeros = map.getTerritories().get(0).getUnits();
        map.resolveAttack(List.of(1, 2), List.of(0, 3), List.of(1, 1), List.of(50, 250));
        assertEquals(2, map.getTerritories().get(1).getOwner());
        assertTrue(map.getTerritories().get(2).getUnits() < 250);
        assertEquals(originalZeros - 50, map.getTerritories().get(0).getUnits());



        // attack from territory not belongs to the player // should do nothing
        map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(-1, -1, -1, 500, 500, 500));
        map.setUnits(List.of(20, 20, 20, -1, -1, -1));
        map.resolveAttack(List.of(1, 1), List.of(0, 1), List.of(2, 3), List.of(10, 10));
        assertTrue(map.getTerritories().get(0).getUnits() == 20);
        assertTrue(map.getTerritories().get(1).getUnits() == 20);
        assertTrue(map.getTerritories().get(3).getUnits() == 500);

        // attack with units more than the territory has // should do nothing
        map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(-1, -1, -1, 500, 500, 500));
        map.setUnits(List.of(20, 20, 20, -1, -1, -1));
        map.resolveAttack(List.of(0, 0), List.of(2, 1), List.of(4, 3), List.of(100, 100));
        assertTrue(map.getTerritories().get(2).getUnits() == 20);
        assertTrue(map.getTerritories().get(1).getUnits() == 20);
        assertTrue(map.getTerritories().get(3).getUnits() == 500);

    }

    @Test
    public void test_move() {
        // test normal move
        // test move from and to territories belong to different player // should do nothing
        MapFactory mapFactory = new v1MapFactory();
        WorldMap map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(-1, -1, -1, 500, 500, 500));
        map.setUnits(List.of(20, 20, 20, -1, -1, -1));
        map.resolveMove(List.of(1, 1), List.of(3, 5), List.of(1, 4), List.of(30, 30));
        assertTrue(map.getTerritories().get(1).getUnits() == 20);
        assertTrue(map.getTerritories().get(3).getUnits() == 500);
        assertTrue(map.getTerritories().get(5).getUnits() == 470);
        assertTrue(map.getTerritories().get(4).getUnits() == 530);
        // test move with 0 unit // should do nothing
        map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(-1, -1, -1, 500, 500, 500));
        map.setUnits(List.of(20, 20, 20, -1, -1, -1));
        map.resolveMove(List.of(1, 0), List.of(3, 1), List.of(4, 2), List.of(0, 0));
        assertTrue(map.getTerritories().get(1).getUnits() == 20);
        assertTrue(map.getTerritories().get(3).getUnits() == 500);
        assertTrue(map.getTerritories().get(5).getUnits() == 500);
        assertTrue(map.getTerritories().get(4).getUnits() == 500);
        // test move with same from and to territories // should do nothing
        map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(-1, -1, -1, 500, 500, 500));
        map.setUnits(List.of(20, 20, 20, -1, -1, -1));
        map.resolveMove(List.of(1, 0), List.of(3, 1), List.of(3, 1), List.of(300, 10));
        assertTrue(map.getTerritories().get(1).getUnits() == 20);
        assertTrue(map.getTerritories().get(3).getUnits() == 500);
        // test move with unconnected // should do nothing current map does not support such case

        // test move with units more than the territory has // should do nothing
        map = mapFactory.make2PlayerMap();
        map.setUnits(List.of(-1, -1, -1, 500, 500, 500));
        map.setUnits(List.of(20, 20, 20, -1, -1, -1));
        map.resolveMove(List.of(1, 0), List.of(3, 1), List.of(4, 0), List.of(30, 100));
        assertTrue(map.getTerritories().get(1).getUnits() == 20);
        assertTrue(map.getTerritories().get(0).getUnits() == 20);
        assertTrue(map.getTerritories().get(3).getUnits() == 470);
        assertTrue(map.getTerritories().get(4).getUnits() == 530);

    }

}
