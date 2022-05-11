package com.example.banksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
    public static void main(String[] args) {
        SpringApplication.run(BankSystemApplication.class, args);
    }
}
