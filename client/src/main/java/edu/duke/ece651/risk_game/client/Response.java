package edu.duke.ece651.risk_game.client;
import edu.duke.ece651.risk_game.server.v1Territory;
import java.util.ArrayList;

public class Response extends Message{
    private final String playerName;
    private final ArrayList<v1Territory> territories;

    public Response(Integer playerID, String playerName, ArrayList<v1Territory> territories) {
        super(playerID);
        this.playerName = playerName;
        this.territories = territories;
    }

    public String getPlayerName() {
        return playerName;
    }

    public ArrayList<v1Territory> getTerritories() {
        return territories;
    }

    public Integer getTerritoryNum() {
        return territories.size();
    }
}
