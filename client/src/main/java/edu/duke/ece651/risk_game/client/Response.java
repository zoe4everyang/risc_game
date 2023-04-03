package edu.duke.ece651.risk_game.client;
import edu.duke.ece651.risk_game.server.Territory;
import java.util.List;

public class Response extends Message{
    private final String playerName;
    private final List<Territory> territories;

    public Response(Integer playerID, String playerName, List<Territory> territories) {
        super(playerID);
        this.playerName = playerName;
        this.territories = territories;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public Integer getTerritoryNum() {
        return territories.size();
    }
}
