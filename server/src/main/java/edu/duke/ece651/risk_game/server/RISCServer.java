package edu.duke.ece651.risk_game.server;

import edu.duke.ece651.risk_game.shared.*;
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
        this.requestHandler = new RequestHandler(this.playerNum);
    }

    public RISCServer(@Value("${risk.game.playerCount}") Integer playerNum) {
        this.playerNum = playerNum;
        this.requestHandler = new RequestHandler(this.playerNum);
    }

    @PostMapping("/start")
    public Message GameStartListen(@RequestBody Message requestBody) {
        try{
            return requestHandler.gameStartHandler();
        }catch(InterruptedException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    @PostMapping("/place")
    public Message PlaceUnitListen(@RequestBody PlacementRequest requestBody) {
        try{
            return requestHandler.placeUnitHandler(requestBody);
        }catch(InterruptedException e) {
            System.out.println(e.toString());
        }
        return null;

    }
    @PostMapping("/act")
    public Message OperationListen(@RequestBody ActionRequest requestBody) {
        try{
            return requestHandler.operationHandler(requestBody);
        }catch(InterruptedException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
