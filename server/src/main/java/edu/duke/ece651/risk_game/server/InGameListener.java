package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/act")
public class InGameListener extends RISCServer {


    @PostMapping("/move/{roomId}")
    public ActionStatus moveActionListen(@PathVariable("roomId") int roomId, @RequestBody ActionRequest requestBody) {
        try{
            return roomSelectionHandler.inGameMoveAttack(roomId, requestBody, "move");
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;

    }

    @PostMapping("/attack/{roomId}")
    public ActionStatus attackActionListen(@PathVariable("roomId") int roomId, @RequestBody ActionRequest requestBody) {
        try{
            return roomSelectionHandler.inGameMoveAttack(roomId, requestBody, "attack");
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;

    }

    @PostMapping("/commit/{roomId}")
    public Response commitActionListen(@PathVariable("roomId") int roomId, @RequestBody HashMap<String, Integer> playerID) {
        try{
            return roomSelectionHandler.inGameCommit(roomId, playerID.get("playerID"));
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;

    }

    @PostMapping("/upgradeUnit/{roomId}")
    public ActionStatus upgradeUnitListen(@PathVariable("roomId") int roomId, @RequestBody UpgradeUnitRequest requestBody) {
        try{
            return roomSelectionHandler.inGameUpgradeUnit(roomId, requestBody);
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;

    }

    @PostMapping("/upgradeTech/{roomId}")
    public ActionStatus upgradeTechListen(@PathVariable("roomId") int roomId, @RequestBody HashMap<String, Object> requestBody) {
        try{
            return roomSelectionHandler.inGameUpgradeTech(roomId, requestBody);
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;
    }
//    @PostMapping("/act")
//    public Message OperationListen(@RequestBody ActionRequest requestBody) {
//        try{
//            return requestHandler.operationHandler(requestBody);
//        }catch(InterruptedException e) {
//            System.out.println("Interrupted Exception in Operation Listening");
//        }
//        return null;
//    }
}
