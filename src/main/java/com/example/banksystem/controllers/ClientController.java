package com.example.banksystem.controllers;

import com.example.banksystem.BankSystemApplication;
import com.example.banksystem.models.Account;
import com.example.banksystem.models.Client;
import com.example.banksystem.models.Transaction;
import com.example.banksystem.repositories.ClientRepository;
import com.example.banksystem.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/registration")
    public String Registration(Model model) {
        model.addAttribute("title", "Registration");
        return "registration";
    }

    @GetMapping("/registration/form")
    public String addClient(@RequestParam String name, @RequestParam String surname, @RequestParam String passport, @RequestParam String address) {
        Client client = new Client();
        client.setName(name);
        client.setSurname(surname);
        client.setPassport(passport);
        client.setAddress(address);

        clientRepository.save(client);

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

        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        List<Account> accounts = currentClient.getAccounts();

        Iterable<Transaction> transactions = transactionRepository.findAll();

        List<Transaction> currentClientTransactions = new ArrayList<>();

        for (Transaction transaction: transactions) {
            if (accounts.contains(transaction.getAccountFrom())) {
                currentClientTransactions.add(transaction);
            }
        }

        model.addAttribute("transactions", currentClientTransactions);
        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Profile");
        model.addAttribute("accounts", accounts);
        return "profile";
    }
}
