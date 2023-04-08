package edu.duke.ece651.risk_game.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RISCBack {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RISCBack.class, args);
        RISCServer.setContext(context);
    }

}