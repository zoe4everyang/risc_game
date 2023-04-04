package edu.duke.ece651.risk_game.server;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
    public String GameStartListen(@RequestBody Message requestBody) {
        // TODO: message object convert to json string
        return requestHandler.gameStartHandler();
    }
    @PostMapping("/place")
    public String PlaceUnitListen(@RequestBody Message requestBody) {
        // TODO: message object convert to json string
        return requestHandler.placeUnitHandler(requestBody);
    }
    @PostMapping("/act")
    public String OperationListen(@RequestBody Message requestBody) {
        // TODO: message object convert to json string
        return requestHandler.operationHandler(requestBody);
    }
}
