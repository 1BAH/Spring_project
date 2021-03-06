package com.example.banksystem.controllers;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Bank;
import com.example.banksystem.models.BankOfficer;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.AccountRepository;
import com.example.banksystem.repositories.BankOfficerRepository;
import com.example.banksystem.repositories.BankRepository;
import com.example.banksystem.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Controller for account pages and forms
 */
@Controller
public class AccountController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BankOfficerRepository bankOfficerRepository;

    /**
     * Page /accounts - the table of all user's accounts
     * @param model
     * @return accounts template
     */
    @GetMapping("/accounts")
    public String accountsPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

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

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

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
    public String addAccount(@RequestParam String type, @RequestParam String bankId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        Account account = new Account(new BigDecimal(0), type, bankRepository.findById(Long.parseLong(bankId)).get(), currentClient);
        accountRepository.save(account);
        return "redirect:/accounts";
    }

    /**
     * Add chosen account to the list of accounts to be closed
     * @param id Account id
     * @return redirects to page "Success"
     */
    @GetMapping("/account/delete/{id}")
    public String deleteAccount(@PathVariable(name = "id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        Account account = accountRepository.findById(id).get();
        BankOfficer bankOfficer = account.getBank().getBankOfficer();
        bankOfficer.addAccountToRemove(account);
        bankOfficerRepository.save(bankOfficer);
        account.setBankOfficer(bankOfficer);
        accountRepository.save(account);
        return "redirect:/account/account-delete-request";
    }

    /**
     * Form for request to close account
     * @param id account id
     * @return "delete-request" template
     */
    @GetMapping("/account/delete/try/{id}")
    public String tryDeleteAccount(@PathVariable(name = "id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        Account account = accountRepository.findById(id).get();

        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Send request");
        model.addAttribute("acc", account);
        return "delete-request";
    }

    /**
     * Success page
     * @return "delete" template
     */
    @GetMapping("/account/account-delete-request")
    public String deleteAccountSuccess(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Request success");
        return "delete";
    }
}