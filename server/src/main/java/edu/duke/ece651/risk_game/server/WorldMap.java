package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;
import java.util.List;

/**
 * This class is used to represent the world map
 */
public interface WorldMap {
    public int getNumTerritories();

    public int shortestPath(int id1, int id2, int playerId);

    public Boolean isConnected(int id1, int id2, int playerId);
    public Boolean isNeighbour(int id1, int id2);
    public List<Integer> getLosers();
    public int getWinner();
    public Boolean checkEnd();
    public List<Territory> getTerritories();
    public void setUnits(List<Integer> palcement);

    // attack given territory by troop t, return true if attack success
    public Boolean makeAttack(int to, Troop t);

    // move troop from one territory to another
    // return false if cannot remove troop from from
    public Boolean makeMove(int from, int to, Troop t);

    // upgrade unit by given amount
    public void upgradeUnit(int territoryId, int UnitId, int amount);

    public int getUnitAvailable();
    public Boolean checkOnMap(int id);
}
