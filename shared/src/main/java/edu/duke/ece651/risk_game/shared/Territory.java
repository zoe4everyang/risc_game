package edu.duke.ece651.risk_game.shared;
import java.util.List;
public interface Territory {
    public void addUnit(int unit);
    public void removeUnit(int unit);
    public void defence(int attacker, int unit);
    public List<Integer> getDistances(); // returns a list of distances to other territories'
    public int getID();
    public String getName();
    public int getOwner();
    public int getUnits();
}