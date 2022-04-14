package com.example.banksystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping("/")
    public String mainPage(Model model) {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        return "home";
    }
}
