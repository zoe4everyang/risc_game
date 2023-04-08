package edu.duke.ece651.risk_game.client;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

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
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.OFF);
        RISCFront front = new RISCFront();
        front.controller.startGame();
    }

}

