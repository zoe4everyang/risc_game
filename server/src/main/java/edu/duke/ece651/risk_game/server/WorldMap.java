package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;
import java.util.List;

public interface WorldMap {
    public int getNumTerritories();
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
    //TODO: check if the territory is on the map
    public Boolean checkOnMap(int id);
}
