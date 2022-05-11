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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
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
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        List<Account> accounts = currentClient.getAccounts();
        Iterable<Transaction> transactions = transactionRepository.findAll();

        List<Transaction> currentClientTransactions = new ArrayList<>();

        for (Transaction transaction: transactions) {
            if (accounts.contains(transaction.getAccountFrom())) {
                currentClientTransactions.add(transaction);
            }
        }

        model.addAttribute("user", currentClient);
        model.addAttribute("transactions", currentClientTransactions);
        model.addAttribute("title", "Transactions");
        return "transactions/transactions";
    }

    @GetMapping("/transactions/make")
    public String makeTransaction() {
        return "redirect:/transactions/step-1";
    }

    @GetMapping("/transactions/step-1")
    public String step1(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        List<Account> accounts = currentClient.getAccounts();
        model.addAttribute("user", currentClient);
        model.addAttribute("accounts", accounts);
        model.addAttribute("title", "Step 1");
        return "transactions/transactions1";
    }

    @GetMapping("/transactions/make/step-1/form")
    public String step1results(Model model, @RequestParam long accountFrom, @RequestParam boolean self) {
        if (self) {
            return "redirect:/transactions/make/step-2-self/" + accountFrom;
        } else {
            return "redirect:/transactions/make/step-2/" + accountFrom;
        }
    }

    @GetMapping("/transactions/make/step-2-self/{fromId}")
    public String step2self(Model model, @PathVariable(value = "fromId") long fromId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        List<Account> accounts = currentClient.getAccounts();
        model.addAttribute("user", currentClient);
        model.addAttribute("accounts", accounts);
        model.addAttribute("title", "Step 2");
        model.addAttribute("chosenAcc", fromId);
        return "transactions/transactions2-self";
    }

    @GetMapping("/transactions/make/step-2/{fromId}")
    public String step2(Model model, @PathVariable(value = "fromId") long fromId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Step 2");
        model.addAttribute("chosenAcc", fromId);
        return "transactions/transactions2";
    }

    @GetMapping("/transactions/make/step-2-self/{fromId}/form")
    public String step2selfResults(Model model, @PathVariable(value = "fromId") long fromId, @RequestParam long accountTo) {
        return "redirect:/transactions/make/step-3/" + fromId + "/" + accountTo;
    }

    @GetMapping("/transactions/make/step-2/{fromId}/form")
    public String step2results(Model model, @PathVariable(value = "fromId") long fromId, @RequestParam long accountTo) {
        return "redirect:/transactions/make/step-3/" + fromId + "/" + accountTo;
    }

    @GetMapping("/transactions/make/step-3/{fromId}/{toId}")
    public String step3(Model model, @PathVariable(value = "fromId") long fromId, @PathVariable(value = "toId") long toId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        List<Account> accounts = currentClient.getAccounts();

        Account accFrom = accountRepository.findById(fromId).get();
        Account accTo = accountRepository.findById(toId).get();

        model.addAttribute("user", currentClient);
        model.addAttribute("accounts", accounts);
        model.addAttribute("title", "Step 3");
        model.addAttribute("fromAcc", accFrom);
        model.addAttribute("toAcc", accTo);
        return "transactions/transactions3";
    }

    @GetMapping("/transactions/make/step-4/{fromId}/{toId}/form")
    public String step4(@RequestParam BigDecimal amount, @PathVariable(value = "fromId") long fromId, @PathVariable(value = "toId") long toId, Model model) {
        Account accFrom = accountRepository.findById(fromId).get();
        Account accTo = accountRepository.findById(toId).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        float commision = 0;
        boolean colored = true;

        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Step 4");
        model.addAttribute("fromAcc", accFrom);
        model.addAttribute("toAcc", accTo);
        model.addAttribute("amount", amount);

        if (accFrom.getBank().getId() != accTo.getBank().getId()) {
            commision = accFrom.getBank().getPercentage();
            colored = false;
        }

        model.addAttribute("colored", colored);
        model.addAttribute("commision", commision);
        model.addAttribute("realAmount", amount.multiply(new BigDecimal(1 + commision / 100)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        return "transactions/transactions4";
    }

    @GetMapping("/transactions/make/{fromId}/{toId}/{amount}/{withCommision}")
    public String makeGetTransaction(@PathVariable(value = "fromId") long fromId, @PathVariable(value = "toId") long toId, @PathVariable(value = "amount") BigDecimal amount, @PathVariable(value = "withCommision") boolean withCommision, Model model) {
        Account accFrom = accountRepository.findById(fromId).get();
        Account accTo = accountRepository.findById(toId).get();

        if (accFrom.withdrawMoney(amount, !withCommision)) {
            accTo.putMoney(amount);
            accountRepository.save(accFrom);
            accountRepository.save(accTo);

            Transaction transaction = new Transaction(accFrom, accTo, amount);
            transactionRepository.save(transaction);
            return "redirect:/transactions";
        } else {
            return "redirect:/transactions/transaction-error";
        }
    }

    @GetMapping("/transactions/transaction-error")
    public String transactionError(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        List<Account> accounts = currentClient.getAccounts();
        model.addAttribute("user", currentClient);
        model.addAttribute("accounts", accounts);
        model.addAttribute("title", "ERROR");
        return "operations/unsuccessful";
    }
}
