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
        Controller controller = new Controller(2);
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


        // second test case
        Controller controller2 = new Controller(3);
        assertTrue(!controller2.checkEnd());
        assertTrue(controller2.getLosers().size() == 0);
        assertEquals(controller2.getWinner(), -1);
        controller2.initGame(List.of(10, 20, 500, 3000, 500, 3000));
        assertEquals(controller2.getTerritories().get(0).getUnits(), 10);
        assertEquals(controller2.getTerritories().get(1).getUnits(), 20);
        assertEquals(controller2.getTerritories().get(2).getUnits(), 500);
        assertEquals(controller2.getTerritories().get(3).getUnits(), 3000);
        assertEquals(controller2.getTerritories().get(4).getUnits(), 500);
        assertEquals(controller2.getTerritories().get(5).getUnits(), 3000);
        assertEquals(controller2.getTerritories().get(1).getOwner(), 0);
        assertEquals(controller2.getTerritories().get(3).getOwner(), 2);
        assertEquals(controller2.getTerritories().get(4).getOwner(), 1);
        end = controller2.step(List.of(2, 1), List.of(3, 2), List.of(1, 0), List.of(1500, 300), List.of(), List.of(), List.of(), List.of());
        assertFalse(end);
        assertEquals(controller2.getTerritories().get(1).getOwner(), 2);
        assertEquals(controller2.getTerritories().get(3).getOwner(), 2);
        assertEquals(controller2.getTerritories().get(0).getOwner(), 1);
        assertEquals(controller2.getTerritories().get(4).getOwner(), 1);
        assertEquals(controller2.getLosers().size(), 1);
        assertEquals(controller2.getLosers().get(0), 0);
        assertEquals(controller2.getWinner(), -1);
        assertFalse(controller2.checkEnd());
        end = controller2.step(List.of(2, 2), List.of(1, 5), List.of(0, 4), List.of(1400, 2990), List.of(), List.of(), List.of(), List.of()); 
        assertFalse(end);
        assertEquals(controller2.getTerritories().get(1).getOwner(), 2);
        assertEquals(controller2.getTerritories().get(3).getOwner(), 2);
        assertEquals(controller2.getTerritories().get(0).getOwner(), 2);
        assertEquals(controller2.getTerritories().get(4).getOwner(), 2);
        assertEquals(controller2.getLosers().size(), 1);
        assertEquals(controller2.getLosers().get(0), 0);
        assertEquals(controller2.getWinner(), -1);
        assertFalse(controller2.checkEnd());

        end = controller2.step(List.of(2), List.of(4), List.of(2), List.of(2000), List.of(), List.of(), List.of(), List.of()); 
        assertTrue(end);
        assertEquals(controller2.getLosers().size(), 2);
        assertEquals(controller2.getLosers().get(0), 0);
        assertEquals(controller2.getLosers().get(1), 1);
        assertEquals(controller2.getWinner(), 2);
        assertTrue(controller2.checkEnd());
    }
}
