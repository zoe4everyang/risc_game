package edu.duke.ece651.risk_game.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.risk_game.shared.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RISCServerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGameStartListen() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Territory t1 = new v1Territory(0, "t1", 0, List.of(0, 1, 1, 2, 2, 3), new v1CombatResolver());
        Territory t2 = new v1Territory(1, "t2", 0, List.of(1, 0, 2, 1, 2, 3), new v1CombatResolver());
        Territory t3 = new v1Territory(2, "t3", 0, List.of(1, 2, 0, 3, 1, 2), new v1CombatResolver());
        Territory t4 = new v1Territory(3, "t4", 1, List.of(2, 1, 3, 0, 2, 1), new v1CombatResolver());
        Territory t5 = new v1Territory(4, "t5", 1, List.of(2, 3, 1, 2, 0, 1), new v1CombatResolver());
        Territory t6 = new v1Territory(5, "t6", 1, List.of(3, 2, 2, 1, 1, 0), new v1CombatResolver());
        List<Territory> territoryList = List.of(t1, t2, t3, t4, t5, t6);
        InitResponse expectResponse = new InitResponse(0, territoryList, false, false, 100);
        String expected = mapper.writeValueAsString(expectResponse);
        mockMvc.perform(post("/start"))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void testPlaceUnitListen() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Territory t1 = new v1Territory(0, "t1", 0, List.of(0, 1, 1, 2, 2, 3), new v1CombatResolver());
        Territory t2 = new v1Territory(1, "t2", 0, List.of(1, 0, 2, 1, 2, 3), new v1CombatResolver());
        Territory t3 = new v1Territory(2, "t3", 0, List.of(1, 2, 0, 3, 1, 2), new v1CombatResolver());
        Territory t4 = new v1Territory(3, "t4", 1, List.of(2, 1, 3, 0, 2, 1), new v1CombatResolver());
        Territory t5 = new v1Territory(4, "t5", 1, List.of(2, 3, 1, 2, 0, 1), new v1CombatResolver());
        Territory t6 = new v1Territory(5, "t6", 1, List.of(3, 2, 2, 1, 1, 0), new v1CombatResolver());
        List<Territory> territoryList = List.of(t1, t2, t3, t4, t5, t6);
        Response expectResponse = new Response(0, territoryList, false, false);
        String expected = mapper.writeValueAsString(expectResponse);
        mockMvc.perform(post("/place"))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void testActionListen() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Territory t1 = new v1Territory(0, "t1", 0, List.of(0, 1, 1, 2, 2, 3), new v1CombatResolver());
        Territory t2 = new v1Territory(1, "t2", 0, List.of(1, 0, 2, 1, 2, 3), new v1CombatResolver());
        Territory t3 = new v1Territory(2, "t3", 0, List.of(1, 2, 0, 3, 1, 2), new v1CombatResolver());
        Territory t4 = new v1Territory(3, "t4", 1, List.of(2, 1, 3, 0, 2, 1), new v1CombatResolver());
        Territory t5 = new v1Territory(4, "t5", 1, List.of(2, 3, 1, 2, 0, 1), new v1CombatResolver());
        Territory t6 = new v1Territory(5, "t6", 1, List.of(3, 2, 2, 1, 1, 0), new v1CombatResolver());
        List<Territory> territoryList = List.of(t1, t2, t3, t4, t5, t6);
        Response expectResponse = new Response(0, territoryList, false, false);
        String expected = mapper.writeValueAsString(expectResponse);
        mockMvc.perform(post("/act"))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

}