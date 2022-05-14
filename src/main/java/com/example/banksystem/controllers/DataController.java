package com.example.banksystem.controllers;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.AccountRepository;
import com.example.banksystem.repositories.ClientRepository;
import com.example.banksystem.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DataController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/data")
    @ResponseBody
    public Map<String, String> getData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        List<Account> accounts = currentClient.getAccounts();
        Map<String, String> map = new LinkedHashMap<>();

        for (Account account: accounts) {
            if (account.getAlert() == 1) {
                map.put(String.valueOf(account.getId()), "Account @" + account.getId() + " was filled up <a href=\"/transaction/" + account.getLastTransactionId() + "\" class=\"nav-link fst-italic text-warning\">See more</a>");
            }
            if (account.getAlert() == 2) {
                map.put(String.valueOf(account.getId()), "Credit account @" + account.getId() + " fee");
            }
        }

        return map;
    }

    @GetMapping("/close/{id}")
    @ResponseBody
    public void close(@PathVariable(name = "id") long id) {
        Account account = accountRepository.findById(id).get();
        account.setAlert((byte) 0);
        accountRepository.save(account);
    }

}
