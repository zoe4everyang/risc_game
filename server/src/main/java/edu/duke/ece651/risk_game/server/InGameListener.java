package edu.duke.ece651.risk_game.server;

import edu.duke.ece651.risk_game.shared.ActionRequest;
import edu.duke.ece651.risk_game.shared.ActionStatus;
import edu.duke.ece651.risk_game.shared.Message;
import edu.duke.ece651.risk_game.shared.Response;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/act")
public class InGameListener extends RISCServer {


    @PostMapping("/move/{roomId}")
    public ActionStatus moveActionListen(@PathVariable("roomId") int roomId, @RequestBody ActionRequest requestBody) {
        try{
            return roomSelectionHandler.inGameAct(roomId, requestBody, "move");
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;

    }

    @PostMapping("/attack/{roomId}")
    public ActionStatus attackActionListen(@PathVariable("roomId") int roomId, @RequestBody ActionRequest requestBody) {
        try{
            return roomSelectionHandler.inGameAct(roomId, requestBody, "attack");
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;

    }

    @PostMapping("/commit/{roomId}")
    public Response commitActionListen(@PathVariable("roomId") int roomId, @RequestBody ActionRequest requestBody) {
        try{
            return roomSelectionHandler.inGameCommit(roomId, requestBody);
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;

    }

    @PostMapping("/upgradeUnit/{roomId}")
    public ActionStatus upgradeUnitListen(@PathVariable("roomId") int roomId, @RequestBody ActionRequest requestBody) {
        try{
            return roomSelectionHandler.inGameAct(roomId, requestBody,"upgradeUnit");
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;

    }

    @PostMapping("/upgradeTech/{roomId}")
    public ActionStatus upgradeTechListen(@PathVariable("roomId") int roomId, @RequestBody ActionRequest requestBody) {
        try{
            return roomSelectionHandler.inGameAct(roomId, requestBody, "upgradeTech");
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
