package edu.duke.ece651.risk_game.server;

import edu.duke.ece651.risk_game.shared.ActionRequest;
import edu.duke.ece651.risk_game.shared.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RISCServer {
    private final Integer playerNum;
    private final RequestHandler requestHandler;

    public RISCServer() {
        this.playerNum = 2;
        this.requestHandler = new RequestHandler(playerNum);
    }

    public RISCServer(@Value("${risk.game.playerCount}") Integer playerNum) {
        this.playerNum = playerNum;
        this.requestHandler = new RequestHandler(playerNum);
    }

    @PostMapping("/start")
    public Message GameStartListen() throws InterruptedException {
        return requestHandler.gameStartHandler();
    }
    @PostMapping("/place")
    public Message PlaceUnitListen(@RequestBody Message requestBody) throws InterruptedException {
        return requestHandler.placeUnitHandler(requestBody);
    }
    @PostMapping("/act")
    public Message ActionListen(@RequestBody ActionRequest requestBody) throws InterruptedException {
        return requestHandler.actionHandler(requestBody);
    }
}
