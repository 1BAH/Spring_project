package com.example.banksystem.controllers;

import com.example.banksystem.models.Bank;
import com.example.banksystem.models.BankOfficer;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.BankOfficerRepository;
import com.example.banksystem.repositories.BankRepository;
import com.example.banksystem.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

/**
 * Controller for bank pages and forms
 */
@Controller
public class BankController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    BankOfficerRepository bankOfficerRepository;

    /**
     * Page /bank - the table of all banks
     * @param model
     * @return bank template
     */
    @GetMapping("/banks")
    public String banksPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        if (!Objects.isNull(currentClient)) {
            model.addAttribute("user", currentClient);
            Iterable<Bank> banks = bankRepository.findAll();
            model.addAttribute("banks", banks);
            model.addAttribute("title", "Banks");
            return "banks/banks";
        } else {
            Iterable<Bank> banks = bankRepository.findAll();
            model.addAttribute("banks", banks);
            model.addAttribute("title", "Banks");
            return "banks/banks-bo";
        }
    }

    /**
     * Page /banks/add - form for creating a new bank
     * @param model
     * @return bank-add template
     */
    @GetMapping("/bo/banks/add")
    public String banksAddPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankOfficer bankOfficer = bankOfficerRepository.findByUsername(authentication.getName());

        if (Objects.isNull(bankOfficer)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-bo";
        }
        model.addAttribute("user", bankOfficer);
        model.addAttribute("title", "Bank creation");
        return "banks/banks-add";
    }

    /**
     * Get the form request, create bank and save it to database
     * @param name bank's name
     * @param percentage bank's percentage (for commission and credit fee)
     * @return redirects to /banks page
     */
    @GetMapping("/bo/banks/add/form")
    public String addBank(@RequestParam String name, @RequestParam String percentage, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankOfficer bankOfficer = bankOfficerRepository.findByUsername(authentication.getName());

        if (Objects.isNull(bankOfficer)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-bo";
        }

        Bank bank = new Bank(name, Float.parseFloat(percentage),  bankOfficer);
        bankRepository.save(bank);
        return "redirect:/banks";
    }
}
