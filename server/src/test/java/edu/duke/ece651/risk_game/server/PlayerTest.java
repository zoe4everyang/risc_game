package edu.duke.ece651.risk_game.server;

import edu.duke.ece651.risk_game.shared.Resource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testUpdateGrade() {
        Player p = new Player(0, "tenki",
                List.of(0), new Resource(90, 100));
        assertEquals(1, p.getTechLevel());
        assertFalse(p.upgradeTechLevel(new HashMap<>() {{
            put(1, 100);
        }}));
        assertEquals(1, p.getTechLevel());
        assertTrue(p.upgradeTechLevel(new HashMap<>() {{
            put(1, 90);
        }}));
        assertEquals(1, p.getTechLevel());
        p.commitUpgrade();
        assertEquals(2, p.getTechLevel());


    }
}
