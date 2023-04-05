package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class RequestHandler {
    private Controller controller;
    private AtomicInteger count;
    private int playerNum;
    //private Queue<Message> msgList;
    private int registerID;
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
        controller = new Controller(playerNum);
        count = new AtomicInteger(0);
        //:msgList = new LinkedList<>();
        registerID = -1;
        attackFrom = new ArrayList<>();
        attackTo = new ArrayList<>();
        attackNum = new ArrayList<>();
        moveFrom = new ArrayList<>();
        moveTo = new ArrayList<>();
        moveNum = new ArrayList<>();
    }

    public Message gameStartHandler() throws InterruptedException {
        int playerID;
        synchronized (this) {
            count.incrementAndGet();
            playerID = registerPlayer();
            if (count.get() < playerNum) {
                wait();
                System.out.println(count.get());
            } else {
                notifyAll();
                count.set(0);
            }
        }
        int unitAvailable = controller.getUnitAvailable();
        List<Territory> territories = controller.getTerritories();
        Message initResponse = new InitResponse(playerID, territories, false, false, unitAvailable);
        return initResponse;
    }

    private int registerPlayer(){
        registerID += 1;
        return registerID;
    }

    // place unit on all territories based on user input
    public Message placeUnitHandler(PlacementRequest msg) throws InterruptedException{
        synchronized (this) {
            List<Integer> unitPlacement = msg.getPlacement();
            controller.initGame(unitPlacement);
            count.incrementAndGet();
            if (count.get() < playerNum) {
                wait();
            } else {
                count.set(0);
                notifyAll();
            }
        }
        List<Territory> territories = controller.getTerritories();
        Message response = new Response(msg.getPlayerID(), territories, false, false);
        return response;
    }

    // move & attack
    public Message operationHandler(ActionRequest msg) throws InterruptedException{

        Boolean isGameEnd;
        int playerID = msg.getPlayerID();
        synchronized (this) {
            count.incrementAndGet();
            for(int i = 0; i < msg.getAttackFrom().size(); i++){
                attackPlayers.add(playerID);
                attackFrom.add(msg.getAttackFrom().get(i));
                attackTo.add(msg.getAttackTo().get(i));
                attackNum.add(msg.getAttackNums().get(i));
            }
            for(int i = 0; i < msg.getMoveFrom().size(); i++){
                movePlayers.add(playerID);
                moveFrom.add(msg.getMoveFrom().get(i));
                moveTo.add(msg.getMoveTo().get(i));
                moveNum.add(msg.getMoveNums().get(i));
            }
            if (count.get() < playerNum) {
                wait();
            } else {
                controller.step(attackPlayers, attackFrom, attackTo, attackNum,
                        movePlayers, moveFrom, moveTo, moveNum);
                count.set(0);
                notifyAll();
            }

        }
        List<Territory> territories = new ArrayList<>();
        Boolean isPlayerLose = controller.checkLose(playerID);
        Message response = new Response(playerID, territories, isPlayerLose, controller.checkEnd());
        return response;
    }
}
