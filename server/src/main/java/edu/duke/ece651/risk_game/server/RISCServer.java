package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RISCServer {
    private final Integer playerNum;
    private final RequestHandler requestHandler;

    public RISCServer() {
        this.playerNum = 3;
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

//    @GetMapping("/init-display")
//    public Message InitDisplayListen(@RequestBody Message requestBody) throws InterruptedException {
//        return requestHandler.initDisplayHandler(requestBody);
//    }
//    @GetMapping("/display")
//    public Message DisplayListen(@RequestBody Message requestBody) throws InterruptedException {
//        return requestHandler.displayHandler(requestBody);
//    }
}
