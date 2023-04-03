package edu.duke.ece651.risk_game.client;


import java.util.List;

public class PlacementRequest extends Message{
    private final List<Integer> placement;

    public PlacementRequest(Integer playerID, List<Integer> placement) {
        super(playerID);
        this.placement = placement;
    }

    public List<Integer> getPlacement() {
        return placement;
    }
}
