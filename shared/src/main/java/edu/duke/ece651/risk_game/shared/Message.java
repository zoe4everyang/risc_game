package edu.duke.ece651.risk_game.shared;

/**
 * This is the base class for all messages sent between client and server.
 * It contains the playerID of the player who sent the message.
 */
public abstract class Message {
    protected final PlayerInfo playerInfo;

    /**
     * This constructor is used to create a message that contains the information of the player.
     * @param playerInfo the information of the player
     */
    public Message(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    /**
     * This constructor is used to create a message that contains the information of the player.
     * @param playerID the ID of the player
     */
    public Message(int playerID) {
        this.playerInfo = new PlayerInfo(playerID, null, 0);
    }

    // This method is used to get the information of the player.
    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }
}
