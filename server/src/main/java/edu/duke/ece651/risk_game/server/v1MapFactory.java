package edu.duke.ece651.risk_game.server;

import java.util.List;

public class v1MapFactory implements MapFactory{

    @Override
    public List<Territory> make2PlayerMap() {
        Territory t1 = new v1Territory(0, "t1", 0, List.of(0, 1, 1, 2, 2, 3), new v1CombatResolver());
        Territory t2 = new v1Territory(1, "t2", 0, List.of(1, 0, 2, 1, 2, 3), new v1CombatResolver());
        Territory t3 = new v1Territory(2, "t3", 0, List.of(1, 2, 0, 3, 1, 2), new v1CombatResolver());
        Territory t4 = new v1Territory(3, "t4", 1, List.of(2, 1, 3, 0, 2, 1), new v1CombatResolver());
        Territory t5 = new v1Territory(4, "t5", 1, List.of(2, 3, 1, 2, 0, 1), new v1CombatResolver());
        Territory t6 = new v1Territory(5, "t6", 1, List.of(3, 2, 2, 1, 1, 0), new v1CombatResolver());
        return List.of(t1, t2, t3, t4, t5, t6);
    }

    @Override
    public List<Territory> make3PlayerMap() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'make3PlayerMap'");
    }

    @Override
    public List<Territory> make4PlayerMap() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'make4PlayerMap'");
    }

    @Override
    public List<Territory> make5PlayerMap() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'make5PlayerMap'");
    }
    
}
