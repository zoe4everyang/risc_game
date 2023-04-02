package edu.duke.ece651.risk_game.client;

import java.util.ArrayList;

public class PlacementRequest extends Message{
    private final ArrayList<Integer> placement;

    public PlacementRequest(Integer playerID, ArrayList<Integer> placement) {
        super(playerID);
        this.placement = placement;
    }

    public ArrayList<Integer> getPlacement() {
        return placement;
    }

    public Integer getPlacementAt(Integer i) {
        return placement.get(i);
    }
}
