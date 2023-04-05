package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;
import java.util.List;

public class v1MapFactory implements MapFactory{

    @Override
    public WorldMap make2PlayerMap() {
        Territory t1 = new v1Territory(0, "t1", 0, List.of(0, 1, 1, 2, 2, 3), new v1CombatResolver());
        Territory t2 = new v1Territory(1, "t2", 0, List.of(1, 0, 2, 1, 2, 3), new v1CombatResolver());
        Territory t3 = new v1Territory(2, "t3", 0, List.of(1, 2, 0, 3, 1, 2), new v1CombatResolver());
        Territory t4 = new v1Territory(3, "t4", 1, List.of(2, 1, 3, 0, 2, 1), new v1CombatResolver());
        Territory t5 = new v1Territory(4, "t5", 1, List.of(2, 3, 1, 2, 0, 1), new v1CombatResolver());
        Territory t6 = new v1Territory(5, "t6", 1, List.of(3, 2, 2, 1, 1, 0), new v1CombatResolver());
        return new v1WorldMap(2, List.of(t1, t2, t3, t4, t5, t6), 300);
    }

    @Override
    public WorldMap make3PlayerMap() {
        Territory t1 = new v1Territory(0, "t1", 0, List.of(0, 1, 1, 2, 2, 3), new v1CombatResolver());
        Territory t2 = new v1Territory(1, "t2", 0, List.of(1, 0, 2, 1, 2, 3), new v1CombatResolver());
        Territory t3 = new v1Territory(2, "t3", 1, List.of(1, 2, 0, 3, 1, 2), new v1CombatResolver());
        Territory t4 = new v1Territory(3, "t4", 2, List.of(2, 1, 3, 0, 2, 1), new v1CombatResolver());
        Territory t5 = new v1Territory(4, "t5", 1, List.of(2, 3, 1, 2, 0, 1), new v1CombatResolver());
        Territory t6 = new v1Territory(5, "t6", 2, List.of(3, 2, 2, 1, 1, 0), new v1CombatResolver());
        return new v1WorldMap(3, List.of(t1, t2, t3, t4, t5, t6), 200);
    }

    // TODO : Check that if the territory map is correctly initialized
    // the distributed map is initialized like this:
    // 11
    // 23
    // 23
    // 44
    @Override
    public WorldMap make4PlayerMap() {
        Territory t1 = new v1Territory(0, "t1", 0, List.of(0, 1, 1, 2, 2, 3, 3, 4), new v1CombatResolver());
        Territory t2 = new v1Territory(1, "t2", 0, List.of(1, 0, 2, 1, 3, 2, 4, 3), new v1CombatResolver());
        Territory t3 = new v1Territory(2, "t3", 1, List.of(1, 2, 0, 1, 1, 2, 2, 3), new v1CombatResolver());
        Territory t4 = new v1Territory(3, "t4", 2, List.of(2, 1, 1, 0, 2, 1, 3, 2), new v1CombatResolver());
        Territory t5 = new v1Territory(4, "t5", 1, List.of(2, 3, 1, 2, 0, 1, 1, 2), new v1CombatResolver());
        Territory t6 = new v1Territory(5, "t6", 2, List.of(3, 2, 2, 1, 1, 0, 2, 1), new v1CombatResolver());
        Territory t7 = new v1Territory(6, "t7", 3, List.of(3, 4, 2, 3, 1, 2, 0, 1), new v1CombatResolver());
        Territory t8 = new v1Territory(7, "t8", 3, List.of(4, 3, 3, 2, 2, 1, 1, 0), new v1CombatResolver());
        return new v1WorldMap(3, List.of(t1, t2, t3, t4, t5, t6, t7, t8), 200);
        // throw new UnsupportedOperationException("Unimplemented method 'make4PlayerMap'");
    }

    // TODO : Check that if the territory map is correctly initialized
    // the distributed map is initialized like this:
    // 114
    // 234
    // 235
    // 5
    @Override
    public WorldMap make5PlayerMap() {
        // TODO : Check that if the territory map is correctly initialized
        Territory t1 = new v1Territory(0, "t1", 0, List.of(0, 1, 2, 1, 2, 3, 2, 3, 4, 3), new v1CombatResolver());
        Territory t2 = new v1Territory(1, "t2", 0, List.of(1, 0, 1, 2, 1, 2, 3, 2, 3, 4), new v1CombatResolver());
        Territory t3 = new v1Territory(2, "t3", 3, List.of(2, 1, 0, 3, 2, 1, 4, 3, 2, 5), new v1CombatResolver());
        Territory t4 = new v1Territory(3, "t4", 1, List.of(1, 2, 3, 0, 1, 2, 1, 2, 3, 2), new v1CombatResolver());
        Territory t5 = new v1Territory(4, "t5", 2, List.of(2, 1, 2, 1, 0, 1, 2, 1, 2, 3), new v1CombatResolver());
        Territory t6 = new v1Territory(5, "t6", 3, List.of(3, 2, 1, 2, 1, 0, 3, 2, 1, 4), new v1CombatResolver());
        Territory t7 = new v1Territory(6, "t7", 1, List.of(2, 3, 4, 1, 2, 3, 0, 1, 2, 1), new v1CombatResolver());
        Territory t8 = new v1Territory(7, "t8", 2, List.of(3, 2, 3, 2, 1, 2, 1, 0, 1, 2), new v1CombatResolver());
        Territory t9 = new v1Territory(8, "t9", 4, List.of(4, 3, 2, 3, 2, 1, 2, 1, 0, 3), new v1CombatResolver());
        Territory t10 = new v1Territory(9, "t10", 4, List.of(3, 4, 5, 2, 3, 4, 1, 2, 3, 0), new v1CombatResolver());
        return new v1WorldMap(3, List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10), 200);
    }
    
}
