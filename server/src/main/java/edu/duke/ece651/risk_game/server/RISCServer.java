package edu.duke.ece651.risk_game.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;

/**
 * RISCServer is the main class for the server
 */
@RestController
public class RISCServer {

    private static ConfigurableApplicationContext context;
    private final Integer playerNum;
    //private final RequestHandler requestHandler;
    protected final RoomSelectionHandler roomSelectionHandler;

    /**
     * Constructor
     */
    public RISCServer() {
        this.playerNum = 2;
        roomSelectionHandler = new RoomSelectionHandler(playerNum);
    }

//    /**
//     * Set the context
//     * @param context the context
//     */
//    public static void setContext(ConfigurableApplicationContext context) {
//        RISCServer.context = context;
//    }

    /**
     * Constructor
     * @param playerNum number of players
     */
    public RISCServer(@Value("${risk.game.playerCount}") Integer playerNum) {
        this.playerNum = playerNum;
        roomSelectionHandler = new RoomSelectionHandler(playerNum);
    }

//    /**
//     * Listen to the start request
//     * @return the message
//     */
//    @PostMapping("/start")
//    public Message GameStartListen() {
//        try {
//            return requestHandler.gameStartHandler();
//        } catch (InterruptedException e) {
//            System.out.println(e.toString());
//        }
//        return null;
//    }
//
//    /**
//     * Listen to the place request
//     * @param requestBody the request body
//     * @return the message
//     */
//    @PostMapping("/place")
//    public Message PlaceUnitListen(@RequestBody PlacementRequest requestBody) {
//        try {
//            return requestHandler.placeUnitHandler(requestBody);
//        } catch (InterruptedException e) {
//            System.out.println(e.toString());
//        }
//        return null;
//
//    }
//
//    /**
//     * Listen to the attack request
//     * @param requestBody the request body
//     * @return the message
//     */
//    @PostMapping("/act")
//    public Message OperationListen(@RequestBody ActionRequest requestBody) {
//        try {
//            return requestHandler.operationHandler(requestBody);
//        } catch (InterruptedException e) {
//            System.out.println("Interrupted Exception in Operation Listening");
//        }
//        return null;
//    }

//    /**
//     * Listen to the game over request
//     */
//    @GetMapping("/gameover")
//    public void gameover() {
//        System.out.println("Game Over");
//        context.close();
//    }
}


