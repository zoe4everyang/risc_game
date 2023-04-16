package edu.duke.ece651.risk_game.server;

import edu.duke.ece651.risk_game.shared.Troop;
import edu.duke.ece651.risk_game.shared.Unit;

public class Action {
    public int from;
    public int to;
    public Troop units;

    public Action(int from, int to, Troop units) {
        this.from = from;
        this.to = to;
        this.units = units;
    }
}
