package edu.duke.ece651.risk_game.server;

public class RequestHandler {

    Controller controller;

    public RequestHandler() {
        controller = new Controller();
    }

    public String GameStartHandler() {
        return controller.start();
    }

    public String PlaceUnitHandler() {
        return controller.place();
    }

    public String OperationHandler() {
        return controller.move();
    }


}
