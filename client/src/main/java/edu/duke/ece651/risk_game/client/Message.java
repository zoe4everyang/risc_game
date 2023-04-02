package edu.duke.ece651.risk_game.client;

public abstract class Message {
    protected final Integer playerID;

    public Message(Integer playerID) {
        this.playerID = playerID;
    }
    public Integer getPlayerID() {
        return playerID;
    }
}
