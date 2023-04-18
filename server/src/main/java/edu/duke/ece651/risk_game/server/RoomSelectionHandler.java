package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;
import org.springframework.context.annotation.Scope;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Scope("singleton")
public class RoomSelectionHandler {

    private HashMap<Integer, RequestHandler> requestHandlerList;   // <roomID, requestHandler>
    private HashMap<String, HashMap<Integer, Integer>> allUserRoomList;  // <username, <roomID, playerID>>

    private int maxRoomID;
    private AtomicInteger joinNum;
    private AtomicInteger count;
    private int playerNum;
    private HashMap<String, String> loginCredentials;
    private List<String> playerList;


    public RoomSelectionHandler(int playerNum) {
        this.requestHandlerList = new HashMap<>();
        this.maxRoomID = -1;
        this.loginCredentials = new HashMap<>();
        this.joinNum =  new AtomicInteger(0);
        this.count =  new AtomicInteger(0);
        this.playerNum = playerNum;
        this.allUserRoomList = new HashMap<>();
        this.playerList = new ArrayList<>();
    }

    public ActionStatus loginHandler(HashMap<String, Object> loginInfo) throws InterruptedException{
        Boolean loginStatus = true;
        if(loginCredentials.containsKey(loginInfo.get("username"))){
            if(loginCredentials.get(loginInfo.get("username")) != loginInfo.get("password")){
                loginStatus = false;
            }
        }
        ActionStatus response = new ActionStatus(loginStatus, 0, null);  //TODO ???
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
            int playerID = allUserRoomList.get(username).get(roomId);
            return new Response(playerID,
                                requestHandlerList.get(roomId).getController().getTerritories(),
                                false, false,
                                getAllUsersInRoom(roomId));
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
                Message response = newGame(roomId, username);
                allUserRoomList.get(username).put(roomId, response.getPlayerInfo().getPlayerID());
                return response;
            }
        // room full, need rejoin
        }else{
            return null;
        }
    }


    private Message newGame(int roomId, String username) throws InterruptedException {
        int playerID;
        synchronized (this) {
            playerList.add(username);
            if(count.get() == playerNum){
                count.set(0);
                playerList.clear();
            }
            playerID = count.get();
            count.incrementAndGet();
            if(count.get() < playerNum) {
                while (count.get() < playerNum) {
                    wait();
                }
            }else{
                requestHandlerList.put(roomId, new RequestHandler(playerNum));
                notifyAll();  //TODO
            }
        }
        Controller newController = requestHandlerList.get(roomId).getController();
        int unitAvailable = newController.getUnitAvailable();
        List<Territory> territories = newController.getTerritories();
        PlayerInfo playerInfo = newController.getPlayerInfo(playerID);
        Message response = new Response(playerInfo, territories, false, false, playerList, unitAvailable);
        return response;
    }


    public Message inGamePlace(int roomID, PlacementRequest request) throws InterruptedException{
        //int playerID = request.getPlayerInfo().getPlayerID();
        return requestHandlerList.get(roomID).placeUnitHandler(request, getAllUsersInRoom(roomID));
    }

    public ActionStatus inGameMoveAttack(int roomID, ActionRequest request, String actionType) throws InterruptedException{
        //int playerID = request.getPlayerInfo().getPlayerID();
        RequestHandler requestHandler = requestHandlerList.get(roomID);
        switch (actionType){
            case "move":
                return requestHandler.moveHandler(request);
            case "attack":
                return requestHandler.attackHandler(request);
        }
        return null;
    }

    public ActionStatus inGameUpgradeUnit(int roomID, UpgradeUnitRequest request) throws InterruptedException {
        RequestHandler requestHandler = requestHandlerList.get(roomID);
        return requestHandler.upgradeUnitHandler(request);
    }

    public ActionStatus inGameUpgradeTech(int roomID, HashMap<String, Object> request) throws InterruptedException {
        RequestHandler requestHandler = requestHandlerList.get(roomID);
        return requestHandler.upgradeTechHandler(request);
    }

    public Response inGameCommit(int roomID, ActionRequest request) throws InterruptedException{
        RequestHandler requestHandler = requestHandlerList.get(roomID);
        return requestHandler.commitHandler(request, getAllUsersInRoom(roomID));
    }

    private List<String> getAllUsersInRoom(int targetRoomID){
        List<String> usernameList = new ArrayList<>(playerNum);
        for (String username : allUserRoomList.keySet()) {
            HashMap<Integer, Integer> roomList = allUserRoomList.get(username);
            for (int roomID : roomList.keySet()) {
                if(roomID == targetRoomID){
                    usernameList.add(username);
                    break;
                }
            }
        }
        return usernameList;
    }
}
