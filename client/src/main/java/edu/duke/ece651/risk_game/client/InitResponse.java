package edu.duke.ece651.risk_game.client;
import edu.duke.ece651.risk_game.server.v1Territory;

import java.util.ArrayList;

public class InitResponse extends Response{
    private final Integer unitAvailable;

    public InitResponse(Integer playerID, String playerName, ArrayList<v1Territory> territories, Integer unitAvailable) {
        super(playerID, playerName, territories);
        this.unitAvailable = unitAvailable;
    }

    public Integer getUnitAvailable() {
        return unitAvailable;
    }
}
