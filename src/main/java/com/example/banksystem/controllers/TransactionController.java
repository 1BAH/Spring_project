package com.example.banksystem.controllers;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Client;
import com.example.banksystem.models.Transaction;
import com.example.banksystem.repositories.AccountRepository;
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

@Controller
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/transactions")
    private String transactionsPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByName(authentication.getName());

        List<Account> accounts = currentClient.getAccounts();
        Iterable<Transaction> transactions = transactionRepository.findAll();

        List<Transaction> currentClientTransactions = new ArrayList<>();

        for (Transaction transaction: transactions) {
            if (accounts.contains(transaction.getAccountFrom())) {
                currentClientTransactions.add(transaction);
            }
        }

        model.addAttribute("transactions", currentClientTransactions);
        return "transactions";
    }

    @GetMapping("/transactions/make")
    public String makeTransaction(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByName(authentication.getName());
        List<Account> accounts = currentClient.getAccounts();
        model.addAttribute("accounts", accounts);
        return "transactions-make";
    }

    @GetMapping("/transactions/make/form")
    public String makeGetTransaction(@RequestParam String amount, @RequestParam String accountFrom, @RequestParam String accountTo) {
        Transaction transaction = new Transaction(accountRepository.findById(Long.parseLong(accountFrom)).get(), accountRepository.findById(Long.parseLong(accountTo)).get(), Float.parseFloat(amount));
        transactionRepository.save(transaction);
        return "redirect:/";
    }
}
