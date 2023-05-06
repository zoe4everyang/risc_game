//package edu.duke.ece651.risk_game.server;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.jupiter.api.Test;
//import edu.duke.ece651.risk_game.shared.CombatResolver;
//import edu.duke.ece651.risk_game.shared.v1CombatResolver;
//public class v1CombatResolverTest {
//   @Test
//   public void test_CombatResolver() {
//       CombatResolver combatResolver = new v1CombatResolver();
//       int attackNum = 3;
//       int defendNum = 2;
//       combatResolver.setSeed(0);
//       int result = combatResolver.resolveCombat(attackNum, defendNum);
//       assertEquals(result, 3);
//
//       // write more test cases for this class
//       result = combatResolver.resolveCombat(118, 20);
//       System.out.println(result);
//       assertEquals(result, 98);
//   }
//
//   // to test the combat resolver, we need to fix the seed
//   @Test
//   public void test2_CombatResolver() {
//       CombatResolver combatResolver = new v1CombatResolver();
//       int attackNum = 100;
//       int defendNum = 2;
//       combatResolver.setSeed(1);
//       int result = combatResolver.resolveCombat(attackNum, defendNum);
//       System.out.println(result);
//       assertEquals(result, 99);
//   }
//
//   @Test
//   public void test3_CombatResolver() {
//       CombatResolver combatResolver = new v1CombatResolver();
//       int attackNum = 100;
//       int defendNum = 2;
//       combatResolver.setSeed(0);
//       int result = combatResolver.resolveCombat(attackNum, defendNum);
//       System.out.println(result);
//       assertEquals(result, 100);
//   }
//
//   @Test
//   public void test4_CombatResolver() {
//       CombatResolver combatResolver = new v1CombatResolver();
//       int attackNum = 100;
//       int defendNum = 50;
//       combatResolver.setSeed(0);
//       int result = combatResolver.resolveCombat(attackNum, defendNum);
//       System.out.println(result);
//       assertEquals(result, 58);
//   }
//
//}
