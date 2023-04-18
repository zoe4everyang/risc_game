//package edu.duke.ece651.risk_game.server;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import java.util.List;
//import edu.duke.ece651.risk_game.shared.*;
//import org.junit.jupiter.api.Test;
//
//public class ControllerTest {
//    @Test
//    public void test_isEnd() {
//        // write test cases for this class
//        Controller controller = new Controller(2);
//        controller.initGame(List.of(5, 5, 5, 5, 100, 10));
//        assertTrue(!controller.checkEnd());
//        assertTrue(controller.getLosers().size() == 0);
//        assertEquals(controller.getWinner(), -1);
//        // assert resources
//        assertEquals(100, controller.getPlayers().get(0).getFoodPoint());
//        assertEquals(100, controller.getPlayers().get(0).getTechPoint());
//        assertEquals(100, controller.getPlayers().get(1).getFoodPoint());
//        assertEquals(100, controller.getPlayers().get(1).getTechPoint());
//        controller.cacheMove(0, 1, 2, List.of(new Unit("test", 0, 5), new Unit("test", 0, 6)));
//        assertEquals(60, controller.getPlayers().get(0).getFoodPoint());
//        assertEquals(3, controller.getTerritories().get(1).getTroopSize());
//        assertEquals(7, controller.getTerritories().get(2).getTroopSize());
//        // after commit each territory should generate a new unit and new resources
//        controller.commit();
//        assertEquals(90, controller.getPlayers().get(0).getFoodPoint());
//        assertEquals(190, controller.getPlayers().get(1).getTechPoint());
//        assertEquals(4, controller.getTerritories().get(1).getTroopSize());
//        assertEquals(8, controller.getTerritories().get(2).getTroopSize());
//        // test upgrade unit fail
//        assertFalse(controller.cacheUpgradeUnit(0, 0, 0, 2));
//        assertFalse(controller.cacheUpgradeUnit(1, 0, 0, 0));
//        // test upgrade technology
//        controller.cacheUpgradeTechnology(1);
//        assertEquals(140, controller.getPlayers().get(1).getTechPoint());
//        assertEquals(1, controller.getPlayers().get(1).getTechLevel());
//        controller.commit();
//        assertEquals(2, controller.getPlayers().get(1).getTechLevel());
//        assertEquals(230, controller.getPlayers().get(1).getTechPoint());
//        // test upgrade unit success
//        assertEquals(0, controller.getTerritories().get(3).getTroop().getUnit(16).getLevel());
//        assertTrue(controller.cacheUpgradeUnit(1, 3, 16, 2));
//        assertEquals(219, controller.getPlayers().get(1).getTechPoint());
//        assertEquals(2, controller.getTerritories().get(3).getTroop().getUnit(16).getLevel());
//
//        assertFalse(controller.cacheUpgradeUnit(1, 3, 16, 1));
//        assertEquals(2, controller.getTerritories().get(3).getTroop().getUnit(16).getLevel());
//        assertEquals(219, controller.getPlayers().get(1).getTechPoint());
//
//
//        // test attack with 20 units
//        assertEquals(160, controller.getPlayers().get(1).getFoodPoint());
//        controller.cacheAttack(1, 4, 2, List.of(new Unit("Test", 0, 20),
//                new Unit("Test", 0, 21),    new Unit("Test", 0, 22),    new Unit("Test", 0, 23),
//                new Unit("Test", 0, 24),    new Unit("Test", 0, 25),    new Unit("Test", 0, 26),
//                new Unit("Test", 0, 27),    new Unit("Test", 0, 28),    new Unit("Test", 0, 29),
//                new Unit("Test", 0, 30),   new Unit("Test", 0, 31),   new Unit("Test", 0, 32),
//                new Unit("Test", 0, 33),   new Unit("Test", 0, 34),   new Unit("Test", 0, 35),
//                new Unit("Test", 0, 36),   new Unit("Test", 0, 37),   new Unit("Test", 0, 38),
//                new Unit("Test", 0, 39)));
//        assertEquals(140, controller.getPlayers().get(1).getFoodPoint());
//        assertEquals(0, controller.getTerritories().get(2).getOwner());
//        controller.commit();
//        assertEquals(1, controller.getTerritories().get(2).getOwner());
//        controller.getTerritories().get(0).defence(new unitTroop(1, List.of(new Unit("Test", 0, 20),
//        new Unit("Test", 0, 21),    new Unit("Test", 0, 22),    new Unit("Test", 0, 23),
//        new Unit("Test", 0, 24),    new Unit("Test", 0, 25),    new Unit("Test", 0, 26),
//        new Unit("Test", 0, 27),    new Unit("Test", 0, 28),    new Unit("Test", 0, 29),
//        new Unit("Test", 0, 30),   new Unit("Test", 0, 31),   new Unit("Test", 0, 32),
//        new Unit("Test", 0, 33),   new Unit("Test", 0, 34),   new Unit("Test", 0, 35),
//        new Unit("Test", 0, 36),   new Unit("Test", 0, 37),   new Unit("Test", 0, 38),
//        new Unit("Test", 0, 39))));
//        controller.getTerritories().get(1).defence(new unitTroop(1, List.of(new Unit("Test", 0, 20),
//        new Unit("Test", 0, 21),    new Unit("Test", 0, 22),    new Unit("Test", 0, 23),
//        new Unit("Test", 0, 24),    new Unit("Test", 0, 25),    new Unit("Test", 0, 26),
//        new Unit("Test", 0, 27),    new Unit("Test", 0, 28),    new Unit("Test", 0, 29),
//        new Unit("Test", 0, 30),   new Unit("Test", 0, 31),   new Unit("Test", 0, 32),
//        new Unit("Test", 0, 33),   new Unit("Test", 0, 34),   new Unit("Test", 0, 35),
//        new Unit("Test", 0, 36),   new Unit("Test", 0, 37),   new Unit("Test", 0, 38),
//        new Unit("Test", 0, 39))));
//        assertTrue(controller.checkEnd());
//        assertEquals(1, controller.getWinner());
//        assertEquals(0, controller.getLosers().get(0));
//        assertEquals(1, controller.getLosers().size());
//    }
//}
