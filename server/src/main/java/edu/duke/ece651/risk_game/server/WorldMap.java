package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;
import java.util.List;

/**
 * This class is used to represent the world map
 */
public interface WorldMap {
    public int getNumTerritories();

    int shortestPath(int id1, int id2, int playerId);

    public Boolean isConnected(int id1, int id2, int playerId);
    public Boolean isNeighbour(int id1, int id2);
    public List<Integer> getLosers();
    public int getWinner();
    public Boolean checkEnd();
    public List<Territory> getTerritories();
    public void setUnits(List<Integer> palcement);
    public void makeAttack(int playerId, int from, int to, int num);
    public void makeMove(int playerId, int from, int to, int num);
    public void resolveAttack(List<Integer> playerIds, List<Integer> fromIds, List<Integer> toIds, List<Integer> unitNums);
    public void resolveMove(List<Integer> playerIds, List<Integer> fromIds, List<Integer> toIds, List<Integer> unitNums);
    public int getUnitAvailable();
    public Boolean checkOnMap(int id);
}
