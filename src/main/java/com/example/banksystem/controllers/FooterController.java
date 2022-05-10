package com.example.banksystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooterController {

    @GetMapping("/authors")
    public String authors(Model model) {
        model.addAttribute("title", "About us");
        return "basic/authors";
    }

    @GetMapping("faq")
    public String faq(Model model) {
        model.addAttribute("title", "FAQ");
        return "basic/faq";
    }
}
