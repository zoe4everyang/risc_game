package edu.duke.ece651.risk_game.server;


import edu.duke.ece651.risk_game.shared.ActionStatus;
import edu.duke.ece651.risk_game.shared.Message;
import edu.duke.ece651.risk_game.shared.PlacementRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class PreGameListener extends RISCServer {


    @PostMapping("/login")
    public ActionStatus playerLoginListen(@RequestBody HashMap<String, Object> requestBody) {
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


    @PostMapping("/place/{roomId}")
    public Message placeUnitListen(@PathVariable("roomId") int roomId, @RequestBody PlacementRequest requestBody) {
        try{
            return roomSelectionHandler.inGamePlace(roomId, requestBody);
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;

    }


}
