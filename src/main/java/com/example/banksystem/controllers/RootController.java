package com.example.banksystem.controllers;

import com.example.banksystem.models.BankOfficer;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.BankOfficerRepository;
import com.example.banksystem.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

/**
 * Controller for root pages
 */
@Controller
public class RootController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BankOfficerRepository bankOfficerRepository;

    /**
     * Root page /
     * @return redirects to page /start if user is not authorised otherwise to /home
     */
    @GetMapping("/")
    public String rootPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        BankOfficer bankOfficer = bankOfficerRepository.findByUsername(authentication.getName());

        if (authentication.getName().equals("root")) {
            return "redirect:/adm";
        } else if (Objects.isNull(currentClient) && Objects.isNull(bankOfficer)) {
            return "redirect:/start";
        } else if (Objects.isNull(bankOfficer)) {
            return "redirect:/home";
        } else {
            return "redirect:/bo/home";
        }
    }

    /**
     * Page /home - home page for authorised users
     * @param model
     * @return home template
     */
    @GetMapping("/home")
    public String homePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Home");
        return "home";
    }

    /**
     * Page /start - home page for unauthorised users
     * @param model
     * @return starter template
     */
    @GetMapping("/start")
    public String startPage(Model model) {
        model.addAttribute("title", "Start work");
        return "starter";
    }

    /**
     * Page /home - home page for bank officers
     * @param model
     * @return home template
     */
    @GetMapping("/bo/home")
    public String homeBOPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankOfficer bankOfficer = bankOfficerRepository.findByUsername(authentication.getName());

        if (Objects.isNull(bankOfficer)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-bo";
        }

        model.addAttribute("user", bankOfficer);
        model.addAttribute("title", "Home");
        return "bankOff/home-bo";
    }
}
