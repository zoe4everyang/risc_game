package edu.duke.ece651.risk_game.server;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
public class v1TerritoryTest {
    @Test
    public void test_Constructor() {
        CombatResolver combatResolver = new v1CombatResolver();
        int id = 1;
        String name = "Territory1";
        int owner = 1;
        List<Integer> distances = new ArrayList<Integer>();
        distances.add(1);
        distances.add(2);
        distances.add(3);
        combatResolver.setSeed(0);
        v1Territory territory = new v1Territory(id, name, owner, distances, combatResolver);
        assertEquals(territory.getID(), id);
        assertEquals(territory.getName(), name);
        assertEquals(territory.getOwner(), owner);
        assertEquals(territory.getDistances(), distances);
        assertEquals(territory.getUnits(), 0);
        int unit = 3;
        territory.addUnit(unit);
        assertEquals(territory.getUnits(), unit);
        territory.removeUnit(unit);
        assertEquals(territory.getUnits(), 0);
    }

    @Test 
    public void test_defence() {
        CombatResolver combatResolver = new v1CombatResolver();
        int id = 1;
        String name = "Territory1";
        int owner = 1;
        List<Integer> distances = new ArrayList<Integer>();
        combatResolver.setSeed(0);
        v1Territory territory = new v1Territory(id, name, owner, distances, combatResolver);
        int attacker = 2;
        int unit = 3;
        territory.addUnit(2);
        territory.defence(attacker, unit);
        assertEquals(territory.getOwner(), attacker);
        assertEquals(territory.getUnits(), 3);
        attacker = 1;
        unit = 118;
        territory.addUnit(17);
        territory.defence(attacker, unit);
        assertEquals(territory.getOwner(), attacker);
        assertEquals(territory.getUnits(), 98);
    }

    @Test
    public void test_getDistances() {
        CombatResolver combatResolver = new v1CombatResolver();
        int id = 1;
        String name = "Territory1";
        int owner = 1;
        List<Integer> distances = new ArrayList<Integer>();
        distances.add(1);
        distances.add(2);
        distances.add(3);
        combatResolver.setSeed(0);
        v1Territory territory = new v1Territory(id, name, owner, distances, combatResolver);
        assertEquals(territory.getDistances(), distances);
    }

    @Test
    public void test_removeUnit() {
        CombatResolver combatResolver = new v1CombatResolver();
        int id = 1;
        String name = "Territory1";
        int owner = 1;
        List<Integer> distances = new ArrayList<Integer>();
        combatResolver.setSeed(0);
        v1Territory territory = new v1Territory(id, name, owner, distances, combatResolver);
        territory.addUnit(2);
        assertEquals(territory.getUnits(), 2);
        territory.removeUnit(1);
        assertEquals(territory.getUnits(), 1);
    }

}
