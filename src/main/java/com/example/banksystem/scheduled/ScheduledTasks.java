package com.example.banksystem.scheduled;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Admin;
import com.example.banksystem.models.Bank;
import com.example.banksystem.repositories.AccountRepository;
import com.example.banksystem.repositories.AdminRepository;
import com.example.banksystem.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
    AdminRepository adminRepository;

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


    @Scheduled(fixedDelay = 500)
    public void init() {
        if (Objects.isNull(adminRepository.findByName("root"))) {
            Admin admin = new Admin("root");
            adminRepository.save(admin);
        }
    }
}
