package com.example.banksystem.controllers;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.AccountRepository;
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
 * Controller for money operations
 */
@Controller
public class MoneyController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    /**
     * Page /withdraw - step 1: choice of account where to withdraw money from
     * @param model
     * @return withdraw-choose template
     */
    @GetMapping("/withdraw")
    public String withdrawMoney(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        List<Account> accounts = currentClient.getAccounts();
        model.addAttribute("accounts", accounts);
        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Withdraw money");
        return "operations/withdraw-choose";
    }

    /**
     * Page /withdraw/choose - step 2: enter the amount of money
     * @param accountId id of chosen account
     * @param model
     * @return withdraw template
     */
    @GetMapping("/withdraw/choose")
    public String withdrawMoneyChoose(@RequestParam String accountId, Model model) {
        model.addAttribute("choice", accountId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Withdraw money");
        return "operations/withdraw";
    }

    /**
     * Trying to complete the operation otherwise redirecting to an error page
     * @param accountId id of chosen account
     * @param amount amount of money to be withdrawn
     * @return redirects to /accounts page if there are enough money on account otherwise to /withdraw/withdraw-error page
     */
    @GetMapping("/withdraw/{accountId}")
    public String withdrawMoneyGet(@PathVariable(value = "accountId") long accountId, @RequestParam BigDecimal amount, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        Account account = accountRepository.findById(accountId).get();
        if (account.withdrawMoney(amount, false)) {
            accountRepository.save(account);
            return "redirect:/accounts";
        }
        return "redirect:/withdraw/withdraw-error";
    }

    /**
     * Page /withdraw/withdraw-error - an error page for withdraw operations
     * @param model
     * @return unsuccessful template
     */
    @GetMapping("/withdraw/withdraw-error")
    public String transactionError(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        List<Account> accounts = currentClient.getAccounts();
        model.addAttribute("user", currentClient);
        model.addAttribute("accounts", accounts);
        model.addAttribute("title", "ERROR");
        return "operations/unsuccessful";
    }

    /**
     * Page /put - step 1: choice of account where to put money on
     * @param model
     * @return put-choose template
     */
    @GetMapping("/put")
    public String putMoney(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        List<Account> accounts = currentClient.getAccounts();
        model.addAttribute("accounts", accounts);
        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Put money");
        return "operations/put-choose";
    }

    /**
     * Page /put/choose - step 2: enter the amount of money
     * @param accountId id of chosen account
     * @param model
     * @return put template
     */
    @GetMapping("/put/choose")
    public String putMoneyChoose(@RequestParam String accountId, Model model) {
        model.addAttribute("choice", accountId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Put money");
        return "operations/put";
    }

    /**
     * Put the money on account and saves it to database
     * @param accountId id of chosen account
     * @param amount amount of money to be withdrawn
     * @return redirects to /accounts page
     */
    @GetMapping("/put/{accountId}")
    public String putMoneyGet(@PathVariable(value = "accountId") long accountId, @RequestParam BigDecimal amount, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }
        Account account = accountRepository.findById(accountId).get();
        account.putMoney(amount);
        accountRepository.save(account);
        return "redirect:/accounts";
    }
}
