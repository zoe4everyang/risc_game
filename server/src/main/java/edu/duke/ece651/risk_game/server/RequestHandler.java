package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is used to handle the request from the client
 */
public class RequestHandler {
    private Controller controller;
    //private int roomID;
    private AtomicInteger count;
    private int playerNum;

    /**
     * Constructor
     * @param playerNum number of players
     */
    public RequestHandler(int playerNum) {
        this.playerNum = playerNum;
        this.controller = new Controller(playerNum);
        this.count = new AtomicInteger(0);
        //this.roomID = roomID;
    }


    // place unit on all territories based on user input
    public Message placeUnitHandler(PlacementRequest msg, List<String> usernameList) throws InterruptedException{
        synchronized (this) {
            List<Integer> unitPlacement = msg.getPlacement();
            controller.initGame(unitPlacement);
            if(count.get() == playerNum) {
                count.set(0);
            }
            count.incrementAndGet();
            if (count.get() < playerNum) {
                while (count.get() < playerNum) {
                    wait();
                }
            } else {
                notifyAll();
            }
        }
        List<Territory> territories = controller.getTerritories();
        Message response = new Response(controller.getPlayerInfo(msg.getPlayerInfo().getPlayerID()), territories, false, false, usernameList);
        return response;
    }


    public ActionStatus moveHandler(ActionRequest msg) throws InterruptedException{
        synchronized (this){
            Boolean status = controller.cacheMove(msg.getPlayerInfo().getPlayerID(), msg.getFrom(), msg.getTo(), msg.getLevelUnits());
            if(status){
                return new ActionStatus(true, null);
            }else{
                return new ActionStatus(false, "Move failed.");
            }
        }
    }

    public ActionStatus attackHandler(ActionRequest msg) throws InterruptedException{
        synchronized (this){
            Boolean status = controller.cacheAttack(msg.getPlayerInfo().getPlayerID(), msg.getFrom(), msg.getTo(), msg.getLevelUnits());
            if(status){
                return new ActionStatus(true, null);
            }else{
                return new ActionStatus(false, "Attack failed.");
            }
        }
    }


    public ActionStatus upgradeUnitHandler(UpgradeUnitRequest msg) throws InterruptedException{
        synchronized (this) {
            Boolean status = controller.cacheUpgradeUnit(msg.getPlayerInfo().getPlayerID(), msg.getTerritoryID(), msg.getLevel(), msg.getLevelUpgraded(), msg.getAmount());
            if(status){
                return new ActionStatus(true, null);
            }else{
                return new ActionStatus(false, "Upgrade unit failed.");
            }
        }
    }

    public ActionStatus upgradeTechHandler(HashMap<String, Object> msg) throws InterruptedException{
        synchronized (this) {
            Boolean status = controller.cacheUpgradeTechnology((int)msg.get("playerID"));
            if(status){
                return new ActionStatus(true, null);
            }else{
                return new ActionStatus(false, "Upgrade Technology failed.");
            }
        }
    }
    public ActionStatus upgradeSpyHandler(HashMap<String, Object> msg) throws InterruptedException{
        synchronized (this) {
            Boolean status = controller.cacheUpgradeSpy((int)msg.get("playerID"), (int)msg.get("territoryID"), (int)msg.get("unitLevel"));
            if(status){
                return new ActionStatus(true, null);
            }else{
                System.out.println("Now it's in handler!");
                return new ActionStatus(false, "Upgrade Spy failed: Insufficient Resources");
            }
        }
    }

    public ActionStatus moveSpyHandler(HashMap<String, Object> msg) throws InterruptedException{
        synchronized (this) {
            Boolean status = controller.cacheMoveSpy((int)msg.get("playerID"), (int)msg.get("territoryID"));
            if(status){
                return new ActionStatus(true, null);
            }else{
                return new ActionStatus(false, "Move Spy failed: Target not adjacent to current position");
            }
        }
    }

    public ActionStatus upgradeCloakHandler(HashMap<String, Object> msg) throws InterruptedException{
        synchronized (this) {
            Boolean status = controller.cacheUpgradeCloak((int)msg.get("playerID"));
            if(status){
                return new ActionStatus(true, null);
            }else{
                return new ActionStatus(false, "Upgrade Cloak failed: Insufficient Resources");
            }
        }
    }

    public ActionStatus setCloakHandler(HashMap<String, Object> msg) throws InterruptedException{
        synchronized (this) {
            Boolean status = controller.cacheSetCloak((int)msg.get("playerID"), (int)msg.get("territoryID"));
            if(status){
                return new ActionStatus(true, null);
            }else{
                return new ActionStatus(false, "Set Cloak failed: Insufficient Resources");
            }
        }
    }

    public Response commitHandler(int playerID, List<String> usernameList) throws InterruptedException{
        synchronized (this){
            if(count.get() >= playerNum) {
                count.set(0);
            }
            count.incrementAndGet();
            if (count.get() < playerNum) {
                while (count.get() < playerNum) {
                    wait();
                }
            } else {
                controller.commit();
                notifyAll();
            }
        }
        PlayerInfo playerInfo = controller.getPlayerInfo(playerID);
        List<Territory> territories = controller.getTerritories();
        Boolean isPlayerLose = controller.checkLose(playerID);
        Boolean isGameEnd = controller.checkEnd();
        // lost player will automatically leave room, so play number -1
        if(isPlayerLose){
            this.playerNum--;
        }
        return new Response(playerInfo, territories, isPlayerLose, isGameEnd, usernameList, -1);
    }

    public Controller getController(){
        return controller;
    }

}

