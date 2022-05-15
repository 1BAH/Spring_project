package com.example.banksystem.controllers;

import com.example.banksystem.models.Bank;
import com.example.banksystem.models.Client;
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
public class BankController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BankRepository bankRepository;

    @GetMapping("/banks")
    public String banksPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        model.addAttribute("user", currentClient);
        Iterable<Bank> banks = bankRepository.findAll();
        model.addAttribute("banks", banks);
        model.addAttribute("title", "Banks");
        return "banks";
    }

    @GetMapping("/banks/add")
    public String banksAddPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Bank creation");
        return "banks-add";
    }

    @GetMapping("/banks/add/form")
    public String addPostAccountPage(@RequestParam String name, @RequestParam String percentage) {
        Bank bank = new Bank(name, Float.parseFloat(percentage));
        bankRepository.save(bank);
        return "redirect:/banks";
    }
}
