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
        Message response = new Response(msg.getPlayerInfo(), territories, false, false, usernameList);
        return response;
    }


    public ActionStatus moveHandler(ActionRequest msg) throws InterruptedException{
        synchronized (this){
            Boolean status = controller.cacheMove(msg.getPlayerInfo().getPlayerID(), msg.getFrom(), msg.getTo(), msg.getUnitIDs());
            return new ActionStatus(status, ); // TODO ????
        }
    }

    public ActionStatus attackHandler(ActionRequest msg) throws InterruptedException{
        synchronized (this){
            Boolean status = controller.cacheAttack(msg.getPlayerInfo().getPlayerID(), msg.getFrom(), msg.getTo(), msg.getUnitIDs());
            return new ActionStatus(status, ); // TODO ????
        }
    }


    public ActionStatus upgradeUnitHandler(UpgradeUnitRequest msg) throws InterruptedException{
        synchronized (this) {
            controller.cacheUpgradeUnit(msg.getPlayerInfo().getPlayerID(), msg.getTerritoryID(), msg.getUnitID(), msg.getLevelUpgraded());
            return new ActionStatus(true, ); //TODO
        }
    }

    public ActionStatus upgradeTechHandler(HashMap<String, Object> msg) throws InterruptedException{
        synchronized (this) {
            // TODO  change client inputController? -> remove upgrade Boolean, only send playerID
            if((Boolean)msg.get("upgradeTech")){
                controller.cacheUpgradeTechnology((int)msg.get("playerID"));
            }
            return new ActionStatus(true, ); //TODO
        }
    }

    public Response commitHandler(ActionRequest msg, List<String> usernameList) throws InterruptedException{
        Boolean status;
        PlayerInfo playerInfo = msg.getPlayerInfo();
        int playerID = playerInfo.getPlayerID();
        synchronized (this){
            if(count.get() == playerNum) {
                count.set(0);
            }
            count.incrementAndGet();
            if (count.get() < playerNum) {
                while (count.get() < playerNum) {
                    wait();
                }
            } else {
                status = controller.commit();
                notifyAll();
            }
        }
        List<Territory> territories = controller.getTerritories();
        Boolean isPlayerLose = controller.checkLose(playerID);
        Boolean isGameEnd = controller.checkEnd();
        return new Response(playerInfo, territories, isPlayerLose, isGameEnd, usernameList);
    }

    public Controller getController(){
        return controller;
    }

    // TODO remove room from list if game is over?
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
