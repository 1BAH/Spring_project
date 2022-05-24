package com.example.banksystem.controllers;

import com.example.banksystem.BankSystemApplication;
import com.example.banksystem.models.Account;
import com.example.banksystem.models.Client;
import com.example.banksystem.models.Transaction;
import com.example.banksystem.repositories.ClientRepository;
import com.example.banksystem.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller for registration and My profile page
 */
@Controller
public class ClientController {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ClientRepository clientRepository;

    /**
     * My Profile page - user's personal data, user's accounts and transactions
     * @param model
     * @return profile template
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        List<Account> accounts = currentClient.getAccounts();

        Iterable<Transaction> transactions = transactionRepository.findAll();

        List<Transaction> currentClientTransactions = new ArrayList<>();
        List<Account> accountRequests = new ArrayList<>();

        for (Transaction transaction: transactions) {
            if (accounts.contains(transaction.getAccountFrom())) {
                currentClientTransactions.add(transaction);
            }
        }

        for (Account account: accounts) {
            if (!Objects.isNull(account.getBankOfficer())) {
                accountRequests.add(account);
            }
        }

        model.addAttribute("transactions", currentClientTransactions);
        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Profile");
        model.addAttribute("accounts", accounts);
        model.addAttribute("accountRequests", accountRequests);
        return "profile";
    }

    @GetMapping("/change-info")
    public String change(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Change personal information");
        return "change-info";
    }

    @GetMapping("/change-info/form")
    public String change(@RequestParam String name, @RequestParam String surname, @RequestParam String passport, @RequestParam String address, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        currentClient.setName(name);
        currentClient.setSurname(surname);
        currentClient.setAddress(address);
        currentClient.setPassport(passport);
        clientRepository.save(currentClient);

        Thread restartThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                BankSystemApplication.restart();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        restartThread.setDaemon(false);
        restartThread.start();

        return "redirect:/login";
    }
}
