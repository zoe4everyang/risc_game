package edu.duke.ece651.risk_game.server;
import edu.duke.ece651.risk_game.shared.*;
import org.springframework.context.annotation.Scope;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Scope("singleton")
public class RoomSelectionHandler {

    private static HashMap<Integer, RequestHandler> requestHandlerList = new HashMap<>();;   // <roomID, requestHandler>
    private static HashMap<String, HashMap<Integer, Integer>> allUserRoomList = new HashMap<>();;  // <username, <roomID, playerID>>
    private int maxRoomID;
    private AtomicInteger joinNum;
    private AtomicInteger count;
    private int playerNum;
    private HashMap<String, String> loginCredentials;
    private List<String> playerList;
    private HashMap<Integer, List<Thread>> threadsListForAll;


    public RoomSelectionHandler(int playerNum) {
        //this.requestHandlerList = new HashMap<>();
        this.maxRoomID = 0;
        this.loginCredentials = new HashMap<>();
        this.joinNum =  new AtomicInteger(0);
        this.count =  new AtomicInteger(0);
        this.playerNum = playerNum;
        //this.allUserRoomList = new HashMap<>();
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
            return new Response(requestHandlerList.get(roomId).getController().getPlayerInfo(playerID),
                    requestHandlerList.get(roomId).getController().getTerritories(),
                    false, false,
                    getAllUsersInRoom(roomId));
        }
        // join new room
        //TODO ensure thread-safe
        synchronized (this) {
            if (!threadsListForAll.containsKey(roomId)) {
                threadsListForAll.put(roomId, new ArrayList<>(playerNum));
            }
            if (joinNum.get() == playerNum) {
                joinNum.set(0);
            }
        }
        synchronized (threadsListForAll.get(roomId)){
            if(roomId == maxRoomID && joinNum.get() < playerNum) {
                joinNum.incrementAndGet();
                if (joinNum.get() < playerNum) {
                    threadsListForAll.get(roomId).add(Thread.currentThread());
                    while (joinNum.get() < playerNum) {
                        threadsListForAll.get(roomId).wait();
                    }
                } else {
                    maxRoomID++;
                    // only notify players in this room
                    threadsListForAll.get(roomId).notifyAll();
                    threadsListForAll.remove(roomId); // clear threads in this room
                }

                Message response = newGame(roomId, username);
                allUserRoomList.get(username).put(roomId, response.getPlayerInfo().getPlayerID());
                System.out.println("after new game" + requestHandlerList.size());
                return response;
            }
        }
        // room full, need rejoin
        return null;
    }


    private Message newGame(int roomId, String username) throws InterruptedException {
        int playerID;

        playerList.add(username);
        if(count.get() >= playerNum){
            count.set(0);
            playerList.clear();
        }
        playerID = count.get();
        count.incrementAndGet();
        if(!requestHandlerList.containsKey(roomId)){
            requestHandlerList.put(roomId, new RequestHandler(playerNum));
        }
//        playerID = count.get();
//        count.incrementAndGet();
//        if(count.get() < playerNum) {
//            while (count.get() < playerNum) {
//                wait();
//            }
//        }else{
//        requestHandlerList.put(roomId, new RequestHandler(playerNum));
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
        System.out.println("in game place before:" + requestHandlerList.size());
        Message msg =  requestHandlerList.get(roomID).placeUnitHandler(request, getAllUsersInRoom(roomID));
        System.out.println("in game place after:" + requestHandlerList.size());
        return msg;
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
        System.out.println(requestHandlerList.size());
        for(int i : requestHandlerList.keySet()){
            System.out.println(i + " " + requestHandlerList.get(i).equals(null));
        }
        RequestHandler requestHandler = requestHandlerList.get(roomID);
        return requestHandler.upgradeTechHandler(request);
    }

    public Response inGameCommit(int roomID, int playerID) throws InterruptedException{
        RequestHandler requestHandler = requestHandlerList.get(roomID);
        Response response = requestHandler.commitHandler(playerID, getAllUsersInRoom(roomID));
        List<String> usernameList = getAllUsersInRoom(roomID);
        for(String username : usernameList){
            int PID = allUserRoomList.get(username).get(roomID);
            if(requestHandlerList.get(roomID).getController().checkLose(PID)){
                allUserRoomList.get(username).remove(roomID);
            }
        }
        checkRoomEnd(roomID);
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
        //
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
