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
    private HashMap<Integer, List<Thread>> threadsListForAll;


    public RoomSelectionHandler(int playerNum) {
        this.requestHandlerList = new HashMap<>();
        this.maxRoomID = 0;
        this.loginCredentials = new HashMap<>();
        this.joinNum =  new AtomicInteger(0);
        this.count =  new AtomicInteger(0);
        this.playerNum = playerNum;
        this.allUserRoomList = new HashMap<>();
        this.playerList = new ArrayList<>();
        this.threadsListForAll = new HashMap<>();
    }

    public ActionStatus loginHandler(HashMap<String, Object> loginInfo) throws InterruptedException{
        Boolean loginStatus = true;
        String errMsg = "";
        String username = (String)loginInfo.get("username");
        if(loginCredentials.containsKey(username)){
            if(loginCredentials.get(username) != loginInfo.get("password")){
                loginStatus = false;
                errMsg = "Log in failed: password does not match.";
            }
        }else{
            allUserRoomList.put(username, new HashMap<>());
        }

        ActionStatus response = new ActionStatus(loginStatus, errMsg);
        return response;
    }


    public Iterable<Integer> getRoomList(String username){
        Set<Integer> roomList = new HashSet<>();
        for(int i : allUserRoomList.get(username).keySet()){
            roomList.add(i);
        }
        System.out.println("roomlist: " + maxRoomID);  // TODO remove last new room if join again
        roomList.add(maxRoomID);
        return roomList;
    }


    public Message joinRoomHandler(String username, int roomId) throws InterruptedException {
        // join existing room
        if(allUserRoomList.get(username) != null && allUserRoomList.get(username).containsKey(roomId)) {
            int playerID = allUserRoomList.get(username).get(roomId);
            //TODO requestHandlerList.getController().commit();
            return new Response(playerID,
                    requestHandlerList.get(roomId).getController().getTerritories(),
                    false, false,
                    getAllUsersInRoom(roomId));
        }
        // join new room
        //TODO ensure thread-safe
        synchronized (this){
            if(roomId == maxRoomID && joinNum.get() < playerNum) {
                if (!threadsListForAll.containsKey(roomId)) {
                    threadsListForAll.put(roomId, new ArrayList<>(playerNum));
                }
                threadsListForAll.get(roomId).add(Thread.currentThread());
                if (joinNum.get() == playerNum) {
                    joinNum.set(0);
                }
                joinNum.incrementAndGet();
                if (joinNum.get() < playerNum) {
                    while (joinNum.get() < playerNum) {
                        wait();
                    }
                } else {
                    maxRoomID++;
                    // only notify players in this room
                    for (Thread thread : threadsListForAll.get(roomId)) {
                        thread.notify();
                    }
                    threadsListForAll.remove(roomId); // clear threads in this room
                }

                Message response = newGame(roomId, username);
                allUserRoomList.get(username).put(roomId, response.getPlayerInfo().getPlayerID());
                return response;
            }
        }
        // room full, need rejoin
        return null;
    }


    private Message newGame(int roomId, String username) throws InterruptedException {
        int playerID;

        playerList.add(username);
        if(count.get() == playerNum){
            count.set(0);
            playerList.clear();
        }
        playerID = count.get();
        count.incrementAndGet();
//        if(count.get() < playerNum) {
//            while (count.get() < playerNum) {
//                wait();
//            }
//        }else{
        requestHandlerList.put(roomId, new RequestHandler(playerNum));
            // only notify players in this room
//            for(Thread thread : threadsListForAll.get(roomId)){
//                thread.notify();
//            }
        //threadsListForAll.remove(roomId); // clear threads in this room
//        }
        System.out.println("Here1");
        Controller newController = requestHandlerList.get(roomId).getController();
        int unitAvailable = newController.getUnitAvailable();
        List<Territory> territories = newController.getTerritories();
        System.out.println("Here2");
        PlayerInfo playerInfo = newController.getPlayerInfo(playerID);
        Message response = new Response(playerInfo, territories, false, false, playerList, unitAvailable);
        return response;
//        int playerID;
//        synchronized (this) {
//            playerList.add(username);
//            if(count.get() == playerNum){
//                count.set(0);
//                playerList.clear();
//            }
//            playerID = count.get();
//            count.incrementAndGet();
//            if(count.get() < playerNum) {
//                while (count.get() < playerNum) {
//                    wait();
//                }
//            }else{
//                requestHandlerList.put(roomId, new RequestHandler(playerNum));
//                // only notify players in this room
//                for(Thread thread : threadsListForAll.get(roomId)){
//                    thread.notify();
//                }
//                threadsListForAll.remove(roomId); // clear threads in this room
//            }
//        }
//        Controller newController = requestHandlerList.get(roomId).getController();
//        int unitAvailable = newController.getUnitAvailable();
//        List<Territory> territories = newController.getTerritories();
//        System.out.print("Here");
//        PlayerInfo playerInfo = newController.getPlayerInfo(playerID);
//        Message response = new Response(playerInfo, territories, false, false, playerList, unitAvailable);
//        return response;
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
        checkRoomEnd(roomID);
        List<String> usernameList = getAllUsersInRoom(roomID);
        for(String username : usernameList){
            int playerID = allUserRoomList.get(username).get(roomID);
            if(requestHandlerList.get(roomID).getController().checkLose(playerID)){
                allUserRoomList.get(username).remove(roomID);
            }
        }
        Response response = requestHandler.commitHandler(request, getAllUsersInRoom(roomID));
        if(response.isLose()){}
        return response;
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

    private void checkRoomEnd(int roomID){
        if(requestHandlerList.get(roomID).getController().checkEnd()){
            List<String> userList = getAllUsersInRoom(roomID);
            for(String username : userList){
                if(allUserRoomList.get(username).containsKey(roomID)){
                    allUserRoomList.get(username).remove(roomID);
                }
            }
            requestHandlerList.remove(roomID);
        }
    }
}
