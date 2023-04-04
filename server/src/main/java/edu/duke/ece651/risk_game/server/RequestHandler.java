package edu.duke.ece651.risk_game.server;
import org.apache.logging.log4j.message.Message;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestHandler {
    private Controller controller;
    private AtomicInteger count = new AtomicInteger(0);
    private int playerNum;
    private Queue<Message> msgList = new LinkedList<>();

    List<Integer> attackPlayers;
    List<Integer> movePlayers;
    List<Integer> attackFrom;
    List<Integer> attackTo;
    List<Integer> attackNum;
    List<Integer> moveFrom;
    List<Integer> moveTo;
    List<Integer> moveNum;


    public RequestHandler(int playerNum) {
        this.playerNum = playerNum;
        v1MapFactory v1MapFactory = new v1MapFactory();
        controller = new Controller(playerNum, v1MapFactory);
    }

    public Message gameStartHandler() throws InterruptedException {
        int playerID = controller.registerPlayer();
        int playerName = controller.getPlayerName(playerID);
        synchronized (this) {
            count.incrementAndGet();
            while (count.get() < playerNum) {
                wait();
            }
            count.set(0);
            notifyAll();
        }
        int unitAvailable = 100;  // TODO: change later, get from controller?
        List<Territory> territories = controller.getTerritories();
        Message initResponse = new initResponse(playerID, playerName, unitAvailable, territories);
        return initResponse;
    }

    // place unit on all territories based on user input
    public Message placeUnitHandler(Message msg) throws InterruptedException{
        synchronized (this) {
            setPlacementByID(msg.getPlayerID, msg.getPlacememt());
            count.incrementAndGet();
            while (count.get() < playerNum) {
                wait();
            }
            List<Integer> unitPlacement = controller.getUnitPlacement();
            controller.initGame(unitPlacement);
            count.set(0);
            notifyAll();
        }
        List<Territory> territories = controller.getTerritories();

        // assign PlayerID
        int playerID = msg.getPlayerID();
        Message response = new response(playerID, controller.getPlayerName(playerID), territories, false);
        return response;
    }

    // move & attack
    public Message operationHandler(Message msg) throws InterruptedException{

        Boolean isGameEnd;
        int playerID = msg.getPlayerID();
        for(int i = 0; i < msg.getAttackFrom().size(); i++){
            attackPlayers.add(playerID);
            attackFrom.add(msg.getAttackFrom().get(i));
            attackTo.add(msg.getAttackTo().get(i));
            attackNum.add(msg.getAttackNum().get(i));
        }
        for(int i = 0; i < msg.getMoveFrom().size(); i++){
            movePlayers.add(playerID);
            moveFrom.add(msg.getMoveFrom().get(i));
            moveTo.add(msg.getMoveTo().get(i));
            moveNum.add(msg.getMoveNum().get(i));
        }
        synchronized (this) {
            count.incrementAndGet();
            while (count.get() < playerNum) {
                wait();
            }
            isGameEnd = controller.step(attackPlayers, attackFrom, attackTo, attackNum,
                                        movePlayers, moveFrom, moveTo, moveNum);
            count.set(0);
            notifyAll();
        }
        List<Territory> territories = new ArrayList<>();
        Message response = new response(playerID, controller.getPlayerName(playerID), territories, isGameEnd);
        return response;
    }
}

//
//    private List<Integer> getAllUnits(Iterable<Message> msgList){
//        List<Integer> unitPlacement = new ArrayList<>();
//        List<Territory> territories = controller.getTerritories();
//        for(Message msg : msgList) {
//            int playerID = msg.getPlayerID();
//            List<Territory> playerTerritory = getPlayerTerritories(playerID, territories);
//            List<Integer> playerUnits = msg.getPlacement();
//            for(int i = 0; i < playerUnits.size(); i++){
//                int territoryID = playerTerritory.get(i).getID();
//                unitPlacement.add(territoryID, playerUnits.get(i));
//            }
//        }
//        return unitPlacement;
//    }

}
