package edu.duke.ece651.risk_game.client;
import edu.duke.ece651.risk_game.shared.Message;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class InputController {
    private RISCClient httpClient;
    private InputStream input;
    private ClientChecker checker;
    private Viewer riskViewer;
    private Message inputMessage;

    public InputController(InputStream input, ClientChecker checker, Viewer riskViewer) {
        this.httpClient = new RISCClient();
        this.input = input;
        this.checker = checker;
        this.riskViewer = riskViewer;
    }

    public void startGame() {
        try {
            InitResponse initResponse = httpClient.sendStart();
            riskViewer.update(initResponse.getTerritories());
        } catch (IOException e) {
            System.out.println("Error while sending start request: " + e.getMessage());
        }
    }

    private void initPhase() {
        List<Integer> unitPlacement = checker.getUnitPlacement(input);
        PlacementRequest placementRequest = new PlacementRequest(checker.getPlayerId(), unitPlacement);

        try {
            Response response = httpClient.sendPlacement(placementRequest);
            riskViewer.update(response.getTerritories());
        } catch (IOException e) {
            System.out.println("Error while sending placement request: " + e.getMessage());
        }
    }

    private void placementPhase() {
        List<Integer> unitPlacement = checker.getUnitPlacement(input);
        PlacementRequest placementRequest = new PlacementRequest(checker.getPlayerId(), unitPlacement);

        try {
            Response response = httpClient.sendPlacement(placementRequest);
            riskViewer.update(response.getTerritories());
        } catch (IOException e) {
            System.out.println("Error while sending placement request: " + e.getMessage());
        }
    }

    private void gamePhase() {
        while (true) {
            ActionRequest actionRequest = checker.getActionRequest(input);

            try {
                Response response = httpClient.sendAction(actionRequest);
                riskViewer.update(response.getTerritories());

                if (response.isEnd()) {
                    System.out.println("Game Over");
                    break;
                }
            } catch (IOException e) {
                System.out.println("Error while sending action request: " + e.getMessage());
            }
        }
    }

    public void run() {
        startGame();
        initPhase();
        placementPhase();
        gamePhase();
    }
}
