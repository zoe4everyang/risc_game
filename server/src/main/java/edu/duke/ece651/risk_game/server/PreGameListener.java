package edu.duke.ece651.risk_game.server;


import edu.duke.ece651.risk_game.shared.Message;
import edu.duke.ece651.risk_game.shared.PlacementRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class PreGameListener extends RISCServer {

    protected final RoomSelectionHandler roomSelectionHandler;

    public PreGameListener(Integer playerNum, RoomSelectionHandler roomSelectionHandler) {
        super(playerNum);
        this.roomSelectionHandler = roomSelectionHandler;
    }

    @PostMapping("/login")
    public Message playerLoginListen(@RequestBody HashMap<String, Object> requestBody) {
        try{
            return roomSelectionHandler.loginHandler(requestBody);
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;
    }

    @GetMapping("/roomId")
    public Iterable<Integer> getRoomListListen(@RequestBody String username) {
        return roomSelectionHandler.getRoomList(username);
    }

    @PostMapping("/join/{roomId}")
    public Message joinRoomListen(@PathVariable("roomId") int roomId, @RequestBody String username) {
        try{
            return roomSelectionHandler.joinRoomHandler(username, roomId);
        }catch(InterruptedException e){
            System.out.println(e);
        }
        return null;
    }


    @PostMapping("/place")
    public Message placeUnitListen(@RequestBody PlacementRequest requestBody) {
        try{
            return requestHandler.placeUnitHandler(requestBody);
        }catch(InterruptedException e) {
            System.out.println(e.toString());
        }
        return null;

    }


}
