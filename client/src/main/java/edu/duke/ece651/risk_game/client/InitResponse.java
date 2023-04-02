package edu.duke.ece651.risk_game.client;

public class InitResponse extends Response{
    private final Integer unitAvailable;

    public InitResponse(Integer playerID, String playerName, ArrayList<Territory> territories, Integer unitAvailable) {
        super(playerID, playerName, territories);
        this.unitAvailable = unitAvailable;
    }

    public Integer getUnitAvailable() {
        return unitAvailable;
    }
}
