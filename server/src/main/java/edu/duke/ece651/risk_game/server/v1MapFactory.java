package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;
import java.util.List;

/**
 * This class is used to make the v1 map
 */
public class v1MapFactory implements MapFactory{

    /**
     * Make a 2 player map
     * @return a 2 player map
     */
    @Override
    public WorldMap make2PlayerMap() {
//        Territory t1 = new v1Territory(0, "t1", 0, List.of(0, 1, 1, 2, 2, 3),
//                20, 30, 10,new evo2CombatResolver());
//        Territory t2 = new v1Territory(1, "t2", 0, List.of(1, 0, 2, 1, 2, 3),
//                20, 30, 10,new evo2CombatResolver());
//        Territory t3 = new v1Territory(2, "t3", 0, List.of(1, 2, 0, 3, 1, 2),
//                20, 30, 10, new evo2CombatResolver());
//        Territory t4 = new v1Territory(3, "t4", 1, List.of(2, 1, 3, 0, 2, 1),
//                20, 30, 10, new evo2CombatResolver());
//        Territory t5 = new v1Territory(4, "t5", 1, List.of(2, 3, 1, 2, 0, 1),
//                20, 30, 10, new evo2CombatResolver());
//        Territory t6 = new v1Territory(5, "t6", 1, List.of(3, 2, 2, 1, 1, 0),
//                20, 30, 10, new evo2CombatResolver());
//        return new v1WorldMap(2, List.of(t1, t2, t3, t4, t5, t6), 300);
        Territory t0 = new v1Territory(0, "t0", 0,
                List.of(0, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5),
                10, 15, 5, new evo2CombatResolver());
        Territory t1 = new v1Territory(1, "t1", 0,
                List.of(1, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4),
                5, 5, 15, new evo2CombatResolver());
        Territory t2 = new v1Territory(2, "t2", 0,
                List.of(1, 1, 0, 2, 1, 2, 1, 2, 2, 3, 3, 4),
                20, 5, 5, new evo2CombatResolver());
        Territory t3 = new v1Territory(3, "t3", 0,
                List.of(2, 1, 2, 0, 1, 1, 3, 2, 3, 2, 3, 3),
                20, 15, 15, new evo2CombatResolver());
        Territory t4 = new v1Territory(4, "t4", 0,
                List.of(2, 1, 1, 1, 0, 1, 1, 1, 2, 2, 2, 3),
                5, 50, 50, new evo2CombatResolver());
        Territory t5 = new v1Territory(5, "t5", 1,
                List.of(3, 2, 2, 1, 1, 0, 2, 1, 2, 1, 2, 2),
                30, 20, 5, new evo2CombatResolver());
        Territory t6 = new v1Territory(6, "t6", 0,
                List.of(2, 2, 1, 2, 1, 2, 0, 1, 1, 2, 2, 3),
                30, 20, 5, new evo2CombatResolver());
        Territory t7 = new v1Territory(7, "t7", 1,
                List.of(3, 2, 2, 2, 1, 1, 1, 0, 1, 1, 1, 2),
                30, 20, 5, new evo2CombatResolver());
        Territory t8 = new v1Territory(8, "t8", 1,
                List.of(3, 3, 2, 3, 2, 2, 1, 1, 0, 2, 1, 2),
                30, 20, 5, new evo2CombatResolver());
        Territory t9 = new v1Territory(9, "t9", 1,
                List.of(4, 3, 3, 2, 2, 1, 2, 1, 2, 0, 1, 1),
                30, 20, 5, new evo2CombatResolver());
        Territory t10 = new v1Territory(10, "t10", 1,
                List.of(4, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 1),
                30, 20, 5, new evo2CombatResolver());
        Territory t11 = new v1Territory(11, "t11", 1,
                List.of(5, 4, 4, 3, 3, 3, 2, 2, 2, 1, 1, 0),
                30, 20, 5, new evo2CombatResolver());
        return new v1WorldMap(4,
                List.of(t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11),
                200);

    }

    /**
     * Make a 3 player map
     * @return a 3 player map
     */
    @Override
    public WorldMap make3PlayerMap() {
        Territory t1 = new v1Territory(0, "t1", 0, List.of(0, 1, 1, 2, 2, 3),
                10, 15, 5, new evo2CombatResolver());
        Territory t2 = new v1Territory(1, "t2", 0, List.of(1, 0, 2, 1, 2, 3),
                5, 5, 15, new evo2CombatResolver());
        Territory t3 = new v1Territory(2, "t3", 1, List.of(1, 2, 0, 3, 1, 2),
                20, 5, 5, new evo2CombatResolver());
        Territory t4 = new v1Territory(3, "t4", 2, List.of(2, 1, 3, 0, 2, 1),
                20, 15, 15, new evo2CombatResolver());
        Territory t5 = new v1Territory(4, "t5", 1, List.of(2, 3, 1, 2, 0, 1),
                5, 50, 50, new evo2CombatResolver());
        Territory t6 = new v1Territory(5, "t6", 2, List.of(3, 2, 2, 1, 1, 0),
                30, 20, 5, new evo2CombatResolver());
        return new v1WorldMap(3, List.of(t1, t2, t3, t4, t5, t6), 200);
    }

    /**
     * Make a 4 player map
     * @return a 4 player map
     */
    @Override
    public WorldMap make4PlayerMap() {
        Territory t0 = new v1Territory(0, "t0", 0,
                List.of(0, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5),
                10, 15, 5, new evo2CombatResolver());
        Territory t1 = new v1Territory(1, "t1", 0,
                List.of(1, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4),
                5, 5, 15, new evo2CombatResolver());
        Territory t2 = new v1Territory(2, "t2", 0,
                List.of(1, 1, 0, 2, 1, 2, 1, 2, 2, 3, 3, 4),
                20, 5, 5, new evo2CombatResolver());
        Territory t3 = new v1Territory(3, "t3", 1,
                List.of(2, 1, 2, 0, 1, 1, 3, 2, 3, 2, 3, 3),
                20, 15, 15, new evo2CombatResolver());
        Territory t4 = new v1Territory(4, "t4", 1,
                List.of(2, 1, 1, 1, 0, 1, 1, 1, 2, 2, 2, 3),
                5, 50, 50, new evo2CombatResolver());
        Territory t5 = new v1Territory(5, "t5", 1,
                List.of(3, 2, 2, 1, 1, 0, 2, 1, 2, 1, 2, 2),
                30, 20, 5, new evo2CombatResolver());
        Territory t6 = new v1Territory(6, "t6", 2,
                List.of(2, 2, 1, 2, 1, 2, 0, 1, 1, 2, 2, 3),
                30, 20, 5, new evo2CombatResolver());
        Territory t7 = new v1Territory(7, "t7", 2,
                List.of(3, 2, 2, 2, 1, 1, 1, 0, 1, 1, 1, 2),
                30, 20, 5, new evo2CombatResolver());
        Territory t8 = new v1Territory(8, "t8", 2,
                List.of(3, 3, 2, 3, 2, 2, 1, 1, 0, 2, 1, 2),
                30, 20, 5, new evo2CombatResolver());
        Territory t9 = new v1Territory(9, "t9", 3,
                List.of(4, 3, 3, 2, 2, 1, 2, 1, 2, 0, 1, 1),
                30, 20, 5, new evo2CombatResolver());
        Territory t10 = new v1Territory(10, "t10", 3,
                List.of(4, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 1),
                30, 20, 5, new evo2CombatResolver());
        Territory t11 = new v1Territory(11, "t11", 3,
                List.of(5, 4, 4, 3, 3, 3, 2, 2, 2, 1, 1, 0),
                30, 20, 5, new evo2CombatResolver());
        return new v1WorldMap(4,
                List.of(t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11),
                200);
//         throw new UnsupportedOperationException("Unimplemented method 'make4PlayerMap'");
    }

    /**
     * Make a 5 player map
     * @return a 5 player map
     */
    @Override
    public WorldMap make5PlayerMap() {
        // TODO : Check that if the territory map is correctly initialized
//        Territory t1 = new v1Territory(0, "t1", 0, List.of(0, 1, 2, 1, 2, 3, 2, 3, 4, 3), new evo2CombatResolver());
//        Territory t2 = new v1Territory(1, "t2", 0, List.of(1, 0, 1, 2, 1, 2, 3, 2, 3, 4), new evo2CombatResolver());
//        Territory t3 = new v1Territory(2, "t3", 3, List.of(2, 1, 0, 3, 2, 1, 4, 3, 2, 5), new evo2CombatResolver());
//        Territory t4 = new v1Territory(3, "t4", 1, List.of(1, 2, 3, 0, 1, 2, 1, 2, 3, 2), new evo2CombatResolver());
//        Territory t5 = new v1Territory(4, "t5", 2, List.of(2, 1, 2, 1, 0, 1, 2, 1, 2, 3), new evo2CombatResolver());
//        Territory t6 = new v1Territory(5, "t6", 3, List.of(3, 2, 1, 2, 1, 0, 3, 2, 1, 4), new evo2CombatResolver());
//        Territory t7 = new v1Territory(6, "t7", 1, List.of(2, 3, 4, 1, 2, 3, 0, 1, 2, 1), new evo2CombatResolver());
//        Territory t8 = new v1Territory(7, "t8", 2, List.of(3, 2, 3, 2, 1, 2, 1, 0, 1, 2), new evo2CombatResolver());
//        Territory t9 = new v1Territory(8, "t9", 4, List.of(4, 3, 2, 3, 2, 1, 2, 1, 0, 3), new evo2CombatResolver());
//        Territory t10 = new v1Territory(9, "t10", 4, List.of(3, 4, 5, 2, 3, 4, 1, 2, 3, 0), new evo2CombatResolver());
//        return new v1WorldMap(3, List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10), 200);
    throw new UnsupportedOperationException("Unimplemented method 'make5PlayerMap'");
    }
    
}
