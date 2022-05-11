package com.example.banksystem.controllers;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Bank;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.AccountRepository;
import com.example.banksystem.repositories.BankRepository;
import com.example.banksystem.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class AccountController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/accounts")
    public String accountsPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        List<Account> accounts = currentClient.getAccounts();
        model.addAttribute("user", currentClient);
        model.addAttribute("accounts", accounts);
        model.addAttribute("title", "Accounts");
        return "accounts";
    }

    @GetMapping("/accounts/add")
    public String addAccountPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        model.addAttribute("user", currentClient);
        Iterable<Bank> banks = bankRepository.findAll();
        model.addAttribute("banks", banks);
        model.addAttribute("title", "Account form");
        return "accounts-add";
    }

    @GetMapping("/accounts/add/form")
    public String addPostAccountPage(@RequestParam String type, @RequestParam String bankId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        Account account = new Account(new BigDecimal(0), type, bankRepository.findById(Long.parseLong(bankId)).get(), currentClient);
        accountRepository.save(account);
        return "redirect:/accounts";
    }
}