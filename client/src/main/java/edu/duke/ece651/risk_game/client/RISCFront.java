package edu.duke.ece651.risk_game.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RISCFront {
    private final InputController controller;
    public RISCFront() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        this.controller = new InputController(input, System.out);
    }
    public static void main(String[] args) throws IOException {
        RISCFront front = new RISCFront();
        front.controller.startGame();
    }

}

