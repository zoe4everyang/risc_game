package edu.duke.ece651.risk_game.server;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
public class v1CombatResolverTest {
    @Test 
    public void test_CombatResolver() {
        CombatResolver combatResolver = new v1CombatResolver();
        int attackNum = 3;
        int defendNum = 2;
        combatResolver.setSeed(0);
        int result = combatResolver.resolveCombat(attackNum, defendNum);
        assertEquals(result, 3);

        // write more test cases for this class
        result = combatResolver.resolveCombat(118, 20);
        System.out.println(result);
        assertEquals(result, 98);
    }   
}
