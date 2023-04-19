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
            Boolean status = controller.cacheUpgradeUnit(msg.getPlayerInfo().getPlayerID(), msg.getTerritoryID(), msg.getLevel(), msg.getLevelUpgraded());
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

    public Response commitHandler(int playerID, List<String> usernameList) throws InterruptedException{
        //int playerID = msg.getPlayerInfo().getPlayerID();
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
        if(isPlayerLose){
            this.playerNum--;
            System.out.println("Player " + playerID + " you lose!");
        }
        return new Response(playerInfo, territories, isPlayerLose, isGameEnd, usernameList);
    }

    public Controller getController(){
        return controller;
    }

}



//    // move & attack
//    public Message operationHandler(ActionRequest msg) throws InterruptedException{
//        //Boolean isGameEnd;
//
//        int playerID = msg.getPlayerID();
//        synchronized (this) {
//            if(count.get() == playerNum) {
//                count.set(0);
//            }
//            count.incrementAndGet();
//            attackPlayers.addAll(Collections.nCopies(msg.getAttackFrom().size(), playerID));
//            attackFrom.addAll(msg.getAttackFrom());
//            attackTo.addAll(msg.getAttackTo());
//            attackNum.addAll(msg.getAttackNums());
//
//            movePlayers.addAll(Collections.nCopies(msg.getMoveFrom().size(), playerID));
//            moveFrom.addAll(msg.getMoveFrom());
//            moveTo.addAll(msg.getMoveTo());
//            moveNum.addAll(msg.getMoveNums());
////            for(int i = 0; i < msg.getAttackFrom().size(); i++){
////                attackPlayers.add(playerID);
////                attackFrom.add(msg.getAttackFrom().get(i));
////                attackTo.add(msg.getAttackTo().get(i));
////                attackNum.add(msg.getAttackNums().get(i));
////            }
////            for(int i = 0; i < msg.getMoveFrom().size(); i++){
////                movePlayers.add(playerID);
////                moveFrom.add(msg.getMoveFrom().get(i));
////                moveTo.add(msg.getMoveTo().get(i));
////                moveNum.add(msg.getMoveNums().get(i));
////            }
//            if (count.get() < playerNum) {
//                while(count.get() < playerNum) {
//                    wait();
//                }
//            } else {
//                controller.step(attackPlayers, attackFrom, attackTo, attackNum,
//                        movePlayers, moveFrom, moveTo, moveNum);
//                attackPlayers.clear();
//                attackFrom.clear();
//                attackTo.clear();
//                attackNum.clear();
//                movePlayers.clear();
//                moveFrom.clear();
//                moveTo.clear();
//                moveNum.clear();
//                notifyAll();
//            }
//        }
//
//        List<Territory> territories = controller.getTerritories();
//        Boolean isPlayerLose = controller.checkLose(playerID);
//        Message response = new Response(playerID, territories, isPlayerLose, controller.checkEnd());
//        return response;
//    }
//}
