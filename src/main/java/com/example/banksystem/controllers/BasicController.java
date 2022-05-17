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

/**
 * Controller of basic pages
 */
@Controller
public class BasicController {
    @Autowired
    ClientRepository clientRepository;

    /**
     * Page /authors - information about authors
     * @param model
     * @return authors-un template if user is not authorised otherwise authors template
     */
    @GetMapping("/authors")
    public String authors(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        model.addAttribute("title", "About us");

        if (Objects.isNull(currentClient)) {
            return "basic/authors-un";
        } else {
            return "basic/authors";
        }
    }

    /**
     * Page /faq - frequently asked questions
     * @param model
     * @return faq-un template if user is not authorised otherwise faq template
     */
    @GetMapping("faq")
    public String faq(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        model.addAttribute("title", "FAQ");

        if (Objects.isNull(currentClient)) {
            return "basic/faq-un";
        } else {
            return "basic/faq";
        }
    }
}
