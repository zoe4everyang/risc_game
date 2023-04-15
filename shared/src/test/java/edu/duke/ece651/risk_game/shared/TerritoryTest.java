package edu.duke.ece651.risk_game.shared;

import org.junit.jupiter.api.Test;

import java.util.List;

public class TerritoryTest {
    @Test
    public void test_terittoryDefence() {
        Territory territory = new v1Territory(0, "t1",
                0, List.of(0, 1),
                30, 5, 5, new evo2CombatResolver());
        
}
