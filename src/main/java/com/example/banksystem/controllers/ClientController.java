package com.example.banksystem.controllers;

import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private RestartEndpoint restartEndpoint;

    @GetMapping("/registration")
    public String Registration(Model model) {
        return "registration";
    }

    @GetMapping("/registration/form")
    public String addClient(@RequestParam String name, @RequestParam String surname, @RequestParam String passport, @RequestParam String address) {
        Client client = new Client();
        client.setName(name);
        client.setSurname(surname);
        client.setPassport(passport);
        client.setAddress(address);

        clientRepository.save(client);

        restartEndpoint.restart();

        return "redirect:/";
    }
}
