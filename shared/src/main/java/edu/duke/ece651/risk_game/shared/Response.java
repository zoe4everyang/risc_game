package edu.duke.ece651.risk_game.shared;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create a message that contains the information of response from the server.
 */
public class Response extends Message {
    //private final String playerName;

    private final List<Territory> territories;
    private final Boolean lose;
    private final Boolean end;
    private final List<String> playerList;

    /**
     * This constructor is used to create a message that contains the information of the player's response.
     *
     * @param playerInfo  the information of the player
     * @param territories the list of territories that the player owns
     * @param lose        the result of the player
     *                    (true: the player loses the game; false: the player does not lose the game)
     * @param end         the state of the game
     *                    (true: the game is end; false: the game is not end)
     */
    @JsonCreator
    public Response(@JsonProperty("playerInfo") PlayerInfo playerInfo,
                    @JsonProperty("territories") List<Territory> territories,
                    @JsonProperty("lose") Boolean lose,
                    @JsonProperty("end") Boolean end,
                    @JsonProperty("playerList") List<String> playerList) {
        super(playerInfo);
        this.territories = new ArrayList<>();
        this.territories.addAll(territories);
        this.lose = lose;
        this.end = end;
        this.playerList = playerList;
    }

    // This method is used to get the list of territories that the player owns.
    public List<Territory> getTerritories() {
        return territories;
    }

    // This method is used to get the result of the player.
    public Boolean isLose() {
        return lose;
    }

    // This method is used to get if the game is end.
    public Boolean isEnd() {
        return end;
    }

    public List<String> getPlayerList() {
        return playerList;
    }
}
