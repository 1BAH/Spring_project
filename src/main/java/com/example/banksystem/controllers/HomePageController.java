package com.example.banksystem.controllers;

import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class HomePageController {
    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/")
    public String mainPage(String model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            return "redirect:/start";
        } else {
            return "redirect:/home";
        }
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/start")
    public String startPage(Model model) {
        model.addAttribute("title", "Start work");
        return "starter";
    }

}
