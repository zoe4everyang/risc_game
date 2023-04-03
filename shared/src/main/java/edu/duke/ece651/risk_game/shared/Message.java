package edu.duke.ece651.risk_game.shared;

/**
 * This is the base class for all messages sent between client and server.
 * It contains the playerID of the player who sent the message.
 */
public abstract class Message {
    protected final Integer playerID;

    /**
     * This constructor is used to create a message that contains the playerID of the player who sent the message.
     *
     * @param playerID the ID of the player
     */
    public Message(Integer playerID) {
        this.playerID = playerID;
    }

    // This method is used to get the playerID of the player who sent the message.
    public Integer getPlayerID() {
        return playerID;
    }
}
