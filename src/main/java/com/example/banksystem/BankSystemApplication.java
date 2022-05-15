package com.example.banksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BankSystemApplication {
    public static String[] arg;
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        arg = args;
        context = SpringApplication.run(BankSystemApplication.class, args);
    }

    public static void restart() {
        context.close();

        context = SpringApplication.run(BankSystemApplication.class, arg);
    }
}
