package edu.duke.ece651.risk_game.client;
import edu.duke.ece651.risk_game.server.Territory;
import java.util.ArrayList;

public class Response extends Message{
    private final String playerName;
    private final ArrayList<Territory> territories;

    public Response(Integer playerID, String playerName, ArrayList<Territory> territories) {
        super(playerID);
        this.playerName = playerName;
        this.territories = territories;
    }

    public String getPlayerName() {
        return playerName;
    }

    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    public Integer getTerritoryNum() {
        return territories.size();
    }
}
