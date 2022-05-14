package com.example.banksystem.scheduled;

import com.example.banksystem.Foo;
import com.example.banksystem.models.Account;
import com.example.banksystem.models.Bank;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.AccountRepository;
import com.example.banksystem.repositories.BankRepository;
import com.example.banksystem.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * Scheduled task
 */
@Component
public class ScheduledTasks {
    @Autowired
    BankRepository bankRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    /**
     * Percentage pays for credit every minute
     */
    @Scheduled(cron = "0 * * * * *")
    public void percents() {
        Iterable<Bank> banks = bankRepository.findAll();
        System.out.println("-----------------------------------------");
        for (Bank bank: banks) {
            System.out.println(bank.getName() + " " + bank.getPercentage() + ": percents updated");
            for (Account account: bank.getAccounts()) {
                account.percents(bank.getPercentage());
                accountRepository.save(account);
            }
        }
    }

    @Scheduled(fixedRate = 5000)
    public void foo() {
        Foo.setBar(Foo.getBar() + 1);
    }


/*
    @Scheduled(fixedRate = 5000)
    public void check() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());


    }
    
 */
}
