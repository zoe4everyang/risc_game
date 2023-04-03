package edu.duke.ece651.risk_game.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ControllerTest {
    @Test
    public void test_isEnd() {
        // write test cases for this class
        Controller controller = new Controller(2, new v1MapFactory());
        assertTrue(!controller.checkEnd());
        assertTrue(controller.getLosers().size() == 0);
        assertEquals(controller.getWinner(), -1);
        controller.initGame(List.of(10, 20, 50, 3000, 2000, 1000));
        assertEquals(controller.getTerritories().get(0).getUnits(), 10);
        assertEquals(controller.getTerritories().get(1).getUnits(), 20);
        assertEquals(controller.getTerritories().get(2).getUnits(), 50);
        assertEquals(controller.getTerritories().get(3).getUnits(), 3000);
        assertEquals(controller.getTerritories().get(4).getUnits(), 2000);
        assertEquals(controller.getTerritories().get(5).getUnits(), 1000);
        assertEquals(controller.getTerritories().get(1).getOwner(), 0);
        assertEquals(controller.getTerritories().get(2).getOwner(), 0);
        assertEquals(controller.getTerritories().get(0).getOwner(), 0);
        Boolean end = controller.step(List.of(1, 1), List.of(3, 4), List.of(1, 2), List.of(1000, 1000, 1000), List.of(), List.of(), List.of(), List.of());
        assertFalse(end);
        assertEquals(controller.getTerritories().get(1).getOwner(), 1);
        assertEquals(controller.getTerritories().get(2).getOwner(), 1);
        assertEquals(controller.getTerritories().get(0).getOwner(), 0);
        end = controller.step(List.of(1), List.of(2), List.of(0), List.of(500), List.of(), List.of(), List.of(), List.of());
        assertTrue(end);
        assertEquals(controller.getWinner(), 1);
        assertEquals(controller.getLosers().size(), 1);
        assertEquals(controller.getLosers().get(0), 0);
        assertEquals(controller.getTerritories().get(1).getOwner(), 1);
        assertEquals(controller.getTerritories().get(2).getOwner(), 1);
        assertEquals(controller.getTerritories().get(0).getOwner(), 1);
    }
}
