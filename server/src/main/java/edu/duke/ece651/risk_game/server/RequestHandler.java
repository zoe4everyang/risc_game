package edu.duke.ece651.risk_game.server;

import edu.duke.ece651.risk_game.shared.*;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestHandler {
    private Controller controller;
    private AtomicInteger count;
    private int playerNum;
    private final Object lock;


    public RequestHandler(int playerNum) {
        this.playerNum = playerNum;
        this.controller = new Controller(playerNum);
        this.count = new AtomicInteger(0);
        this.lock = new Object();
    }

    public Message gameStartHandler() throws InterruptedException {
        int playerID;
        synchronized (lock) {
            playerID = count.incrementAndGet();
            while (count.get() < playerNum) {
                wait();
            }
            count.set(0);
            notifyAll();
        }
        //return new InitResponse(playerID, controller.getTerritories(), controller.checkWin(playerID), controller.checkEnd(), controller.getUnitAvailable(playerID));
        return new InitResponse(playerID, controller.getTerritories(), false, controller.checkEnd(), 100);
    }

    // place unit on all territories based on user input
    public Message placeUnitHandler(Message msg) throws InterruptedException{
        RedisTemplate<String, Object> redisTemplate = RedisUtil.getRedisTemplate();
        redisTemplate.opsForList().rightPush("Placement", msg);

        synchronized (lock) {
            count.incrementAndGet();
            if (count.get() == playerNum) {
                // all players have sent their commands, start to update the game state
                List<Territory> territories = controller.getTerritories();
                List<Integer> placement = new ArrayList<>(Collections.nCopies(territories.size(), 0));
                while (redisTemplate.opsForList().size("Placement") > 0) {
                    PlacementRequest cmd = (PlacementRequest) redisTemplate.opsForList().leftPop("Placement");
                    assert cmd != null;
                    List<Integer> placeList = cmd.getPlacement();
                    for (int i = 0; i < placeList.size(); i++) {
                        if (territories.get(i).getOwner() == cmd.getPlayerID()) {
                            placement.set(i, placement.get(i) + placeList.get(i));
                        }
                    }
                }
                // update game state
                controller.initGame(placement);
                count.set(0);
                lock.notifyAll();
            } else {
                lock.wait();
            }
        }
        //return new Response(msg.getPlayerID(), controller.getTerritories(), controller.checkWin(), controller.checkEnd());
        return new Response(msg.getPlayerID(), controller.getTerritories(), false, controller.checkEnd());
    }

    // move & attack
    public Message actionHandler(ActionRequest msg) throws InterruptedException{

        RedisTemplate<String, Object> redisTemplate = RedisUtil.getRedisTemplate();
        redisTemplate.opsForList().rightPush("Commands", msg);

        synchronized (lock) {
            count.incrementAndGet();
            if (count.get() == playerNum) {
                // all players have sent their commands, start to update the game state
                List<Integer> movePlayers = new ArrayList<>();
                List<Integer> attackFrom = new ArrayList<>();
                List<Integer> attackTo = new ArrayList<>();
                List<Integer> attackNum = new ArrayList<>();
                List<Integer> attackPlayers = new ArrayList<>();
                List<Integer> moveFrom = new ArrayList<>();
                List<Integer> moveTo = new ArrayList<>();
                List<Integer> moveNum = new ArrayList<>();
                while (redisTemplate.opsForList().size("Commands") > 0) {
                    ActionRequest cmd = (ActionRequest) redisTemplate.opsForList().leftPop("Commands");
                    assert cmd != null;
                    movePlayers.addAll(Collections.nCopies(cmd.getMoveFrom().size(),cmd.getPlayerID()));
                    attackPlayers.addAll(Collections.nCopies(cmd.getAttackFrom().size(),cmd.getPlayerID()));
                    attackFrom.addAll(cmd.getAttackFrom());
                    attackTo.addAll(cmd.getAttackTo());
                    attackNum.addAll(cmd.getAttackNums());
                    moveFrom.addAll(cmd.getMoveFrom());
                    moveTo.addAll(cmd.getMoveTo());
                    moveNum.addAll(cmd.getMoveNums());
                }
                // update game state
                controller.step(attackPlayers, attackFrom, attackTo, attackNum,
                        movePlayers, moveFrom, moveTo, moveNum);
                count.set(0);
                lock.notifyAll();
            } else {
                lock.wait();
            }
        }
        //return new Response(msg.getPlayerID(), controller.getTerritories(), controller.checkWin(), controller.checkEnd());
        return new Response(msg.getPlayerID(), controller.getTerritories(), false, controller.checkEnd());
    }

//    public Message displayHandler(Message msg) throws InterruptedException {
//        int playerID = msg.getPlayerID();
//        return new Response(playerID, controller.getPlayerName(playerID), controller.getLosers(), controller.getTerritories(), controller.checkEnd());
//    }
//
//    public Message initDisplayHandler(Message msg) {
//        int playerID = msg.getPlayerID();
//        return new InitResponse(playerID, controller.getPlayerName(playerID), controller.getLosers(),
//                controller.getTerritories(), controller.checkEnd(), controller.getUnitAvailable(playerID));
//    }
}
