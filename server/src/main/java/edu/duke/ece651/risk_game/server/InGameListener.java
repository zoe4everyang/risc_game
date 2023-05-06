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

    @PostMapping("/upgradeSpy/{roomId}")
    public ActionStatus upgradeSpyListen(@PathVariable("roomId") int roomId, @RequestBody HashMap<String, Object> requestBody) {
        try{
            return roomSelectionHandler.inGameUpgradeSpy(roomId, requestBody);
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;
    }

    @PostMapping("/moveSpy/{roomId}")
    public ActionStatus moveSpyListen(@PathVariable("roomId") int roomId, @RequestBody HashMap<String, Object> requestBody) {
        try{
            return roomSelectionHandler.inGameMoveSpy(roomId, requestBody);
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;
    }

    @PostMapping("/upgradeCloak/{roomId}")
    public ActionStatus upgCloakListen(@PathVariable("roomId") int roomId, @RequestBody HashMap<String, Object> requestBody) {
        try{
            return roomSelectionHandler.inGameUpgradeCloak(roomId, requestBody);
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;
    }

    @PostMapping("/setCloak/{roomId}")
    public ActionStatus setCloakListen(@PathVariable("roomId") int roomId, @RequestBody HashMap<String, Object> requestBody) {
        try{
            return roomSelectionHandler.inGameSetCloak(roomId, requestBody);
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;
    }

    @PostMapping("/commit/{roomId}")
    public Response commitActionListen(@PathVariable("roomId") int roomId, @RequestBody HashMap<String, Integer> playerID) {
        try{
            Response response = roomSelectionHandler.inGameCommit(roomId, playerID.get("playerID"));
            if (response.isEnd()) {
                if (response.isLose()) {
                    System.out.println("Player " + playerID + " you lose!");
                } else {
                    System.out.println("Player " + playerID + " you win!");
                }
                System.out.println(response.getPlayerInfo().getPlayerID());
                for (Territory t : response.getTerritories()) {
                    System.out.println("Owner: " +t.getOwner());
                }
                for (String s : response.getPlayerList()) {
                    System.out.println("Username: " + s);
                }
            }
            return response;
        }catch(InterruptedException e) {
            System.out.println(e);
        }
        return null;
    }

}
