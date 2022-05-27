package com.example.banksystem.controllers;

import com.example.banksystem.BankSystemApplication;
import com.example.banksystem.models.Admin;
import com.example.banksystem.models.BankOfficerPrototype;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.AdminRepository;
import com.example.banksystem.repositories.BankOfficerPrototypeRepository;
import com.example.banksystem.repositories.BankOfficerRepository;
import com.example.banksystem.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @Autowired
    BankOfficerRepository bankOfficerRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    BankOfficerPrototypeRepository bankOfficerPrototypeRepository;

    @GetMapping("/bo/registration")
    public String registrationBO(Model model) {
        model.addAttribute("title", "Registration");
        return "bankOff/reg-bo";
    }

    @GetMapping("/bo/registration/form")
    public String addBankOfficer(@RequestParam String name, @RequestParam String pass) {
        Admin admin = adminRepository.findByName("root");

        BankOfficerPrototype bankOfficer = new BankOfficerPrototype(name, pass, admin);

        bankOfficerPrototypeRepository.save(bankOfficer);

        return "bankOff/reg2-bo";
    }

    /**
     * Page /registration - registration of the new user
     * @param model
     * @return registration template
     */
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("title", "Registration");
        return "registration";
    }

    /**
     * Get the form request, create account and save it to database.
     * Also restarts application context in order to refresh database connection.
     * @param name
     * @param surname
     * @param passport
     * @param address
     * @return redirects to root
     */
    @GetMapping("/registration/form")
    public String addClient(@RequestParam String name, @RequestParam String surname, @RequestParam String passport, @RequestParam String address) {
        Client client = new Client();
        client.setName(name);
        client.setSurname(surname);
        client.setPassport(passport);
        client.setAddress(address);

        clientRepository.save(client);

        Thread restartThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                BankSystemApplication.restart();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        restartThread.setDaemon(false);
        restartThread.start();

        return "redirect:/";
    }
}
