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

import javax.websocket.ClientEndpoint;
import java.util.List;

@Controller
public class MoneyController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/accounts/withdraw")
    public String withdrawMoney(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByName(authentication.getName());
        List<Account> accounts = currentClient.getAccounts();
        model.addAttribute("accounts", accounts);
        return "withdraw-choose";
    }

    @GetMapping("/accounts/withdraw/choose")
    public String withdrawMoneyChoose(@RequestParam String accountId, Model model) {
        model.addAttribute("choice", accountId);
        return "withdraw";
    }

    @GetMapping("accounts/withdraw/{accountId}")
    public String withdrawMoneyGet(@PathVariable(value = "accountId") long accountId, @RequestParam float amount, Model model) {
        Account account = accountRepository.findById(accountId).get();
        account.withdrawMoney(amount);
        accountRepository.save(account);
        return "redirect:/accounts";
    }

}
