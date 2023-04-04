package edu.duke.ece651.risk_game.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ServerViewer {
    Controller game;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public ServerViewer(Controller game){
        this.game = game;
    }

    public void display() {
        for (int i = 0; i < game.getPlayers(); ++i) {
            printPlayerId(i);
        }
    }
    public List<Integer> parseString(String s){
        List<Integer> list = new ArrayList<Integer>();
        String[] split = s.split(" ");
        for (String str : split){
            list.add(Integer.parseInt(str));
        }
        return list;
    }

    public void run() throws IOException{
        System.out.println("There are" + game.getPlayers() + " players");
        game.initGame(List.of(100, 100, 200, 200, 300, 300));
        System.out.println("The initial game state is: ");
        display();
        while (!game.checkEnd()) {
            System.out.println("Enter exit to exit");
            String s = reader.readLine();
            if (s.equals("exit")) {
                break;
            }
            System.out.println("Please input move Operations");
            List<List<Integer>> moveOperations = readOperations();
            System.out.println("Please input attack operations");
            List<List<Integer>> attackOperations = readOperations();

            // operations[0] playerId
            // operations[1] fromId
            // operations[2] toId
            // operations[3] units
            game.step(attackOperations.get(0), attackOperations.get(1), attackOperations.get(2), attackOperations.get(3),
                    moveOperations.get(0), moveOperations.get(1), moveOperations.get(2), moveOperations.get(3));
            display();
        }

        System.out.println("Game Over, the winner is " + game.getWinner() + "!!");
    }
    public List<List<Integer>> readOperations() {
        List<List<Integer>> operations = new ArrayList<List<Integer>>();
        for(int i = 0; i < 4; ++i) {
            if (i == 0) {
                System.out.println("please input PlayerIds");
            } else if (i == 1) {
                System.out.println("please input fromIds");
            } else if (i == 2) {
                System.out.println("please input toIds");
            } else {
                System.out.println("please input units");
            }
            try {
                String s = reader.readLine();
                List<Integer> list = parseString(s);
                operations.add(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return operations;
    }
    public void printTerritory(Territory t) {
        System.out.println("Territory " + t.getName() + " has " + t.getUnits() + " units.");

        System.out.print("Neighbours: ");
        for (int i = 0; i < t.getDistances().size(); ++i) {
            if (t.getDistances().get(i) == 1) {
                System.out.print(game.getTerritories().get(i).getName() + " ");
            }
        }
        System.out.println();
    }

    public void printPlayerId(int playerId) {
        System.out.println("Player " + playerId + " has territories: ");
        for (Territory t : game.getTerritories()) {
            if (t.getOwner() == playerId) {
                printTerritory(t);
            }
        }
        System.out.println();
    }
}
