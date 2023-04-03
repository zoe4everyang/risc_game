package edu.duke.ece651.risk_game.server;

public interface MapFactory {
    public WorldMap make2PlayerMap();
    public WorldMap make3PlayerMap();
    public WorldMap make4PlayerMap();
    public WorldMap make5PlayerMap();
}
