package edu.duke.ece651.risk_game.client;
import edu.duke.ece651.risk_game.server.Territory;

import java.util.List;

public class InitResponse extends Response{
    private final Integer unitAvailable;

    public InitResponse(Integer playerID, String playerName, List<Territory> territories, Integer unitAvailable) {
        super(playerID, playerName, territories);
        this.unitAvailable = unitAvailable;
    }

    public Integer getUnitAvailable() {
        return unitAvailable;
    }
}
