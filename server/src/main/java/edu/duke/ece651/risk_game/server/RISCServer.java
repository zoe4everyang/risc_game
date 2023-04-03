package edu.duke.ece651.risk_game.server;

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
        this.requestHandler = new RequestHandler();
    }

    public RISCServer(@Value("${risk.game.playerCount}") Integer playerNum) {
        this.playerNum = playerNum;
        this.requestHandler = new RequestHandler();
    }

    @PostMapping("/start")
    public String GameStartListen(@RequestBody Message requestBody) {
        return requestHandler.GameStartHandler(requestBody);
    }
    @PostMapping("/place")
    public String PlaceUnitListen(@RequestBody Message requestBody) {
        return requestHandler.PlaceUnitHandler(requestBody);
    }
    @PostMapping("/Movecommit")
    public String OperationListen(@RequestBody Message requestBody) {
        return requestHandler.OperationHandler(requestBody);
    }
}
