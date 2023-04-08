package edu.duke.ece651.risk_game.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * This is the main class for the server
 */
@SpringBootApplication
public class RISCBack {

    /**
     * This is the main function for the server
     * @param args command line arguments
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RISCBack.class, args);
        RISCServer.setContext(context);
    }

}