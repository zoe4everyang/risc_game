package edu.duke.ece651.risk_game.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import edu.duke.ece651.risk_game.shared.*;
import org.junit.jupiter.api.Test;

public class ControllerTest {
    @Test
    public void test_isEnd() {
        // write test cases for this class
        Controller controller = new Controller(2);
        assertTrue(!controller.checkEnd());
        assertTrue(controller.getLosers().size() == 0);
        assertEquals(controller.getWinner(), -1);
    }
}
