package edu.duke.ece651.risk_game.server;

import edu.duke.ece651.risk_game.shared.ActionRequest;
import edu.duke.ece651.risk_game.shared.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/act")
public class InGameListener extends RISCServer {

    @PostMapping("/move")
    public Message moveActionListen(@RequestBody ActionRequest requestBody) {
        try{
            return requestHandler.moveHandler(requestBody);
        }catch(InterruptedException e) {
            System.out.println(e.toString());
        }
        return null;

    }

    @PostMapping("/attack")
    public Message attackActionListen(@RequestBody ActionRequest requestBody) {
        try{
            return requestHandler.attackHandler(requestBody);
        }catch(InterruptedException e) {
            System.out.println(e.toString());
        }
        return null;

    }

    @PostMapping("/commit")
    public Message commitActionListen(@RequestBody ActionRequest requestBody) {
        try{
            return requestHandler.commitHandler(requestBody);
        }catch(InterruptedException e) {
            System.out.println(e.toString());
        }
        return null;

    }

    @PostMapping("/upgradeUnit")
    public Message upgradeUnitListen(@RequestBody ActionRequest requestBody) {
        try{
            return requestHandler.upgradeUnitHandler(requestBody);
        }catch(InterruptedException e) {
            System.out.println(e.toString());
        }
        return null;

    }

    @PostMapping("/upgradeTech")
    public Message upgradeTechListen(@RequestBody ActionRequest requestBody) {
        try{
            return requestHandler.upgradeTechHandler(requestBody);
        }catch(InterruptedException e) {
            System.out.println(e.toString());
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
