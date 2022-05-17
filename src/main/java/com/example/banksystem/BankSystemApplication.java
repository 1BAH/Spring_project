package com.example.banksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Bank system application
 *
 * @author Kalinin Iwan
 * @author Zaretskaia Mariia
 *
 * @version 2.0.RELEASE
 */
@SpringBootApplication
@EnableScheduling
public class BankSystemApplication {
    public static String[] arg;
    
    private static ConfigurableApplicationContext context;

    /**
     * Runs the application
     * @param args
     */
    public static void main(String[] args) {
        arg = args;
        context = SpringApplication.run(BankSystemApplication.class, args);
    }

    /**
     * Restarts the application
     */
    public static void restart() {
        context.close();

        context = SpringApplication.run(BankSystemApplication.class, arg);
    }
}
