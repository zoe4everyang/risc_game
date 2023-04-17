package edu.duke.ece651.risk_game.server;

import edu.duke.ece651.risk_game.shared.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomSelectionHandler {

    private HashMap<Integer, RequestHandler> requestHandlerList;   // <roomID, requestHandler>
    private HashMap<String, HashMap<Integer, Integer>> allUserRoomList;  // <username, <roomID, playerID>>
    private int maxRoomID;
    private int maxPlayerID;
    private AtomicInteger joinNum;
    private AtomicInteger count;
    private int playerNum;
    private HashMap<String, String> loginCredentials;


    public RoomSelectionHandler(List<List<Integer>> roomList, int playerNum) {
        this.requestHandlerList = new HashMap<>();
        this.maxRoomID = -1;
        this.maxPlayerID = -1;
        this.loginCredentials = new HashMap<>();
        this.joinNum =  new AtomicInteger(0);
        this.count =  new AtomicInteger(0);
        this.playerNum = playerNum;
        this.allUserRoomList = new HashMap<>();
    }

    public Message loginHandler(HashMap<String, Object> loginInfo) throws InterruptedException{
        Boolean loginStatus = true;
        if(loginCredentials.containsKey(loginInfo.get("username"))){
            if(loginCredentials.get(loginInfo.get("username")) != loginInfo.get("password")){
                loginStatus = false;
            }
        }
        Message response = new ActionStatus(loginStatus);
        return response;
    }


    public Iterable<Integer> getRoomList(String username){
        Set<Integer> roomList = allUserRoomList.get(username).keySet();
        roomList.add(maxRoomID);
        return roomList;
    }


    public Message joinRoomHandler(String username, int roomId) throws InterruptedException {
        // join existing room
        if(allUserRoomList.get(username).containsKey(roomId)){
            return new Response(allUserRoomList.get(username).get(roomId), requestHandlerList.get(roomId).getController().getTerritories(), //TODO change later, list of RequestHandlers/controllers
                    false, false);
        // join new room
        }else if(roomId == maxRoomID){
            synchronized(this){
                if(joinNum.get() == playerNum) {
                    joinNum.set(0);
                }
                joinNum.incrementAndGet();
                if(joinNum.get() < playerNum){
                    while(joinNum.get() < playerNum){
                        wait();
                    }
                }else{
                    maxRoomID++;
                    notifyAll();  //TODO change later: only notify players in this room
                }
                Message response = newGame(roomId);
                allUserRoomList.get(username).put(roomId, response.getPlayerID());
                return response;
            }
        // room full, need rejoin
        }else{
            return null;
        }
    }

    private Message newGame(int roomId) throws InterruptedException {
        int playerID;
        synchronized (this) {
            playerID = registerNewPlayer();
            if(count.get() == playerNum){
                count.set(0);
            }
            count.incrementAndGet();
            if(count.get() < playerNum) {
                while (count.get() < playerNum) {
                    wait();
                }
            }else{
                notifyAll();  //TODO
            }
        }
        requestHandlerList.put(roomId, new RequestHandler(playerNum, roomId));
        Controller newController = requestHandlerList.get(roomId).getController();
        int unitAvailable = newController.getUnitAvailable();
        List<Territory> territories = newController.getTerritories();
        Message response = new Response(playerID, territories, false, false, unitAvailable);
        return response;
    }

    private int registerNewPlayer(){
        maxPlayerID += 1;
        return maxPlayerID;
    }
}
