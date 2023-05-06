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
//    public void test_cacheMoveSpy() {
//        // construct a controller
//        Controller controller = new Controller(4);
//        // init game
//        controller.initGame(List.of(5, 5, 5, 5, 100, 10, 10, 10, 10, 10, 100, 10));
//
//        // upgrade spy for player 0
//        controller.cacheUpgradeSpy(0, 0, 0);
//        // only 4 troops left
//        assertEquals(4, controller.getTerritories().get(0).getTroopSize());
//        // assert spy position at 0
//        assertEquals(0, controller.getPlayers().get(0).getSpyPos());
//        // move spy to 2
//        assertTrue(controller.cacheMoveSpy(0, 2));
//        // assert spy position at 2
//        assertEquals(2, controller.getPlayers().get(0).getSpyPos());
//        // move spy to 11 and fail
//        assertFalse(controller.cacheMoveSpy(0, 11));
//        // assert spy position at 2
//        assertEquals(2, controller.getPlayers().get(0).getSpyPos());
//        // move spy to 6
//        assertTrue(controller.cacheMoveSpy(0, 6));
//        // assert spy position at 6
//        assertEquals(6, controller.getPlayers().get(0).getSpyPos());
//        // move spy to 0 and fail
//        assertFalse(controller.cacheMoveSpy(0, 0));
//        // assert spy position at 6
//        assertEquals(6, controller.getPlayers().get(0).getSpyPos());
//        // move spy to 7
//        assertTrue(controller.cacheMoveSpy(0, 7));
//        // assert spy position at 7
//        assertEquals(7, controller.getPlayers().get(0).getSpyPos());
//
//    }
//    @Test
//    public void test_cacheSetCloak() {
//        // construct a controller
//        Controller controller = new Controller(4);
//        // wait to generate some resources
//        for (int i = 0; i < 50; ++i) {
//            controller.commit();
//        }
//        for (int i = 0; i < 5; ++i) {
//            controller.cacheUpgradeTechnology(1);
//            controller.commit();
//        }
//        // upgrade cloak
//        assertTrue(controller.cacheUpgradeCloak(1));
//        // assert visibility of player 0
//        assertTrue(controller.getPlayers().get(0).getVisible().get(0));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(1));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(2));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(3));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(4));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(5));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(6));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(7));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(8));
//        // assert tech point 625
//        assertEquals(3925, controller.getPlayers().get(1).getTechPoint());
//        assertTrue(controller.cacheSetCloak(1, 4));
//        controller.resetVisibility(0);
//        // assert visibility of player 0
//        assertTrue(controller.getPlayers().get(0).getVisible().get(0));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(1));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(2));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(3));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(4));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(5));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(6));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(7));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(8));
//
//    }
//    @Test
//    public void test_cacheUpgradeSpy() {
//        Controller controller = new Controller(4);
//        controller.initGame(List.of(5, 5, 5, 5, 100, 10, 10, 10, 10, 10, 100, 10));
//        // assert troop size in 0 is 5
//        assertEquals(5, controller.getTerritories().get(0).getTroopSize());
//        // assert init tech point 100
//        assertEquals(100, controller.getPlayers().get(0).getTechPoint());
//        // assert init can spy false
//        assertFalse(controller.getPlayers().get(0).hasSpy());
//        controller.cacheUpgradeSpy(0, 0, 0);
//        // assert tech point 80
//        assertEquals(80, controller.getPlayers().get(0).getTechPoint());
//        // assert spy position at 0
//        assertEquals(0, controller.getPlayers().get(0).getSpyPos());
//        // assert has spy true
//        assertTrue(controller.getPlayers().get(0).hasSpy());
//        // assert troop size at 0 is 4
//        assertEquals(4, controller.getTerritories().get(0).getTroopSize());
//
//
//    }
//    @Test
//    public void test_upgradeCloak() {
//        Controller controller = new Controller(4);
//        // assert init tech point 100
//        assertEquals(100, controller.getPlayers().get(0).getTechPoint());
//        // assert init can Cloak false
//        assertFalse(controller.getPlayers().get(0).getCanCloak());
//        controller.cacheUpgradeCloak(0);
//        // assert tech point 100 due to insufficient tech Level
//        assertEquals(100, controller.getPlayers().get(0).getTechPoint());
//        // assert can cloak false
//        assertFalse(controller.getPlayers().get(0).getCanCloak());
//        // wait to generate some resources
//        for (int i = 0; i < 50; ++i) {
//            controller.commit();
//        }
//        for (int i = 0; i < 5; ++i) {
//            controller.cacheUpgradeTechnology(0);
//            controller.commit();
//        }
//        assertEquals(725, controller.getPlayers().get(0).getTechPoint());
//        assertEquals(6, controller.getPlayers().get(0).getTechLevel());
//        assertFalse(controller.getPlayers().get(0).getCanCloak());
//        controller.cacheUpgradeCloak(0);
//        assertTrue(controller.getPlayers().get(0).getCanCloak());
//        assertEquals(625, controller.getPlayers().get(0).getTechPoint());
//
//    }
//    @Test
//    public void test_visibility() {
//        // construct a controller
//        Controller controller = new Controller(4);
//        // test visibility of players
//        assertEquals(4, controller.getPlayers().size());
//        // visibility of first player should be [true, true, true,true, true, false, true, false, false, false, false, false]
//        assertTrue(controller.getPlayers().get(0).getVisible().get(0));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(1));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(2));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(3));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(4));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(5));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(6));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(7));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(8));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(9));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(10));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(11));
//        controller.cSetCloak(3);
//        controller.cSetCloak(6);
//        controller.resetVisibility(0);
//        assertFalse(controller.getPlayers().get(0).getVisible().get(3));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(6));
//        controller.cSetSpyPos(0, 3);
//        controller.resetVisibility(0);
//        assertTrue(controller.getPlayers().get(0).getVisible().get(3));
//        assertFalse(controller.getPlayers().get(0).getVisible().get(6));
//        controller.cSetSpyPos(0, 6);
//        controller.resetVisibility(0);
//        assertFalse(controller.getPlayers().get(0).getVisible().get(3));
//        assertTrue(controller.getPlayers().get(0).getVisible().get(6));
//
//
//    }
//
//   @Test
//   public void test_isEnd() {
//       // write test cases for this class
//       Controller controller = new Controller(2);
//       controller.initGame(List.of(5, 5, 5, 5, 100, 10));
//       assertTrue(!controller.checkEnd());
//       assertTrue(controller.getLosers().size() == 0);
//       assertEquals(controller.getWinner(), -1);
//       // assert resources
//       assertEquals(100, controller.getPlayers().get(0).getFoodPoint());
//       assertEquals(100, controller.getPlayers().get(0).getTechPoint());
//       assertEquals(100, controller.getPlayers().get(1).getFoodPoint());
//       assertEquals(100, controller.getPlayers().get(1).getTechPoint());
//       controller.cacheMove(0, 1, 2,  List.of(6, 0, 0, 0, 0, 0, 0));
//       assertEquals(60, controller.getPlayers().get(0).getFoodPoint());
//       assertEquals(3, controller.getTerritories().get(1).getTroopSize());
//       assertEquals(7, controller.getTerritories().get(2).getTroopSize());
//       // after commit each territory should generate a new unit and new resources
//       controller.commit();
//       assertEquals(90, controller.getPlayers().get(0).getFoodPoint());
//       assertEquals(190, controller.getPlayers().get(1).getTechPoint());
//       assertEquals(4, controller.getTerritories().get(1).getTroopSize());
//       assertEquals(8, controller.getTerritories().get(2).getTroopSize());
//       // test upgrade unit fail
//       assertFalse(controller.cacheUpgradeUnit(0, 0, 0, 2));
//       assertFalse(controller.cacheUpgradeUnit(1, 0, 0, 0));
//       // test upgrade technology
//       controller.cacheUpgradeTechnology(1);
//       assertEquals(140, controller.getPlayers().get(1).getTechPoint());
//       assertEquals(1, controller.getPlayers().get(1).getTechLevel());
//       controller.commit();
//       assertEquals(2, controller.getPlayers().get(1).getTechLevel());
//       assertEquals(230, controller.getPlayers().get(1).getTechPoint());
//       // test upgrade unit success
//       assertEquals(0, controller.getTerritories().get(3).getTroop().getUnit(16).getLevel());
//       assertTrue(controller.cacheUpgradeUnit(1, 3, 16, 2));
//       assertEquals(219, controller.getPlayers().get(1).getTechPoint());
//       assertEquals(2, controller.getTerritories().get(3).getTroop().getUnit(16).getLevel());
//
//       assertFalse(controller.cacheUpgradeUnit(1, 3, 16, 1));
//       assertEquals(2, controller.getTerritories().get(3).getTroop().getUnit(16).getLevel());
//       assertEquals(219, controller.getPlayers().get(1).getTechPoint());
//
//
//       // test attack with 20 units
//       assertEquals(160, controller.getPlayers().get(1).getFoodPoint());
//       controller.cacheAttack(1, 4, 2, List.of(new Unit("Test", 0, 20),
//               new Unit("Test", 0, 21),    new Unit("Test", 0, 22),    new Unit("Test", 0, 23),
//               new Unit("Test", 0, 24),    new Unit("Test", 0, 25),    new Unit("Test", 0, 26),
//               new Unit("Test", 0, 27),    new Unit("Test", 0, 28),    new Unit("Test", 0, 29),
//               new Unit("Test", 0, 30),   new Unit("Test", 0, 31),   new Unit("Test", 0, 32),
//               new Unit("Test", 0, 33),   new Unit("Test", 0, 34),   new Unit("Test", 0, 35),
//               new Unit("Test", 0, 36),   new Unit("Test", 0, 37),   new Unit("Test", 0, 38),
//               new Unit("Test", 0, 39)));
//       assertEquals(140, controller.getPlayers().get(1).getFoodPoint());
//       assertEquals(0, controller.getTerritories().get(2).getOwner());
//       controller.commit();
//       assertEquals(1, controller.getTerritories().get(2).getOwner());
//       controller.getTerritories().get(0).defence(new unitTroop(1, List.of(new Unit("Test", 0, 20),
//       new Unit("Test", 0, 21),    new Unit("Test", 0, 22),    new Unit("Test", 0, 23),
//       new Unit("Test", 0, 24),    new Unit("Test", 0, 25),    new Unit("Test", 0, 26),
//       new Unit("Test", 0, 27),    new Unit("Test", 0, 28),    new Unit("Test", 0, 29),
//       new Unit("Test", 0, 30),   new Unit("Test", 0, 31),   new Unit("Test", 0, 32),
//       new Unit("Test", 0, 33),   new Unit("Test", 0, 34),   new Unit("Test", 0, 35),
//       new Unit("Test", 0, 36),   new Unit("Test", 0, 37),   new Unit("Test", 0, 38),
//       new Unit("Test", 0, 39))));
//       controller.getTerritories().get(1).defence(new unitTroop(1, List.of(new Unit("Test", 0, 20),
//       new Unit("Test", 0, 21),    new Unit("Test", 0, 22),    new Unit("Test", 0, 23),
//       new Unit("Test", 0, 24),    new Unit("Test", 0, 25),    new Unit("Test", 0, 26),
//       new Unit("Test", 0, 27),    new Unit("Test", 0, 28),    new Unit("Test", 0, 29),
//       new Unit("Test", 0, 30),   new Unit("Test", 0, 31),   new Unit("Test", 0, 32),
//       new Unit("Test", 0, 33),   new Unit("Test", 0, 34),   new Unit("Test", 0, 35),
//       new Unit("Test", 0, 36),   new Unit("Test", 0, 37),   new Unit("Test", 0, 38),
//       new Unit("Test", 0, 39))));
//       assertTrue(controller.checkEnd());
//       assertEquals(1, controller.getWinner());
//       assertEquals(0, controller.getLosers().get(0));
//       assertEquals(1, controller.getLosers().size());
//   }
//}
