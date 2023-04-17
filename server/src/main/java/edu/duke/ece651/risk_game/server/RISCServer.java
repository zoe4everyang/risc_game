package edu.duke.ece651.risk_game.server;

import edu.duke.ece651.risk_game.shared.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RISCServer {
    protected final Integer playerNum;
    protected final RequestHandler requestHandler;

    public RISCServer() {
        this.playerNum = 2;
        this.requestHandler = new RequestHandler(this.playerNum);
    }

    public RISCServer(@Value("${risk.game.playerCount}") Integer playerNum) {
        this.playerNum = playerNum;
        this.requestHandler = new RequestHandler(this.playerNum);
    }


}
