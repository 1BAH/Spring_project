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

@Controller
public class AccountController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/account")
    public String accountPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByName(authentication.getName());
        Iterable<Account> account = accountRepository.findAll();
        model.addAttribute("account", account);
        return "account";
    }

    @GetMapping("/accounts/add")
    public String addAccountPage(Model model) {
        Iterable<Bank> banks = bankRepository.findAll();
        model.addAttribute("banks", banks);
        return "add";
    }
    @GetMapping("/account/add/form")
    public String addPostAccountPage(@RequestParam String type, @RequestParam String amount, @RequestParam String bankId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByName(authentication.getName());
        Account account = new Account(Float.parseFloat(amount), type.charAt(0), bankRepository.findById(Long.parseLong(bankId)).get(), currentClient);
        accountRepository.save(account);
        return "redirect:/accounts";
    }

}