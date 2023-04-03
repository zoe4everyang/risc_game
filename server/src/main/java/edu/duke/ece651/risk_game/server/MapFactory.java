package edu.duke.ece651.risk_game.server;
import java.util.List;
public interface MapFactory {
    public List<Territory> make2PlayerMap();
    public List<Territory> make3PlayerMap();
    public List<Territory> make4PlayerMap();
    public List<Territory> make5PlayerMap();
}
