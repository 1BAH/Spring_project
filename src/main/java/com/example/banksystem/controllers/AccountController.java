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

/**
 * Controller for account pages
 */
@Controller
public class AccountController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    ClientRepository clientRepository;

    /**
     * Page /accounts - the table of all user's accounts
     * @param model
     * @return accounts template
     */
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

    /**
     * Page /accounts/add - form for the creation of a new account
     * @param model
     * @return accounts-add template
     */
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

    /**
     * Get the form request, create account and save it to database
     * @param type Credit/Debit
     * @param bankId id of bank where to open the account
     * @return redirects to /accounts page
     */
    @GetMapping("/accounts/add/form")
    public String addAccount(@RequestParam String type, @RequestParam String bankId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        Account account = new Account(new BigDecimal(0), type, bankRepository.findById(Long.parseLong(bankId)).get(), currentClient);
        accountRepository.save(account);
        return "redirect:/accounts";
    }
}