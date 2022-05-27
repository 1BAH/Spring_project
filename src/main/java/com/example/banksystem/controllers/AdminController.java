package com.example.banksystem.controllers;

import com.example.banksystem.BankSystemApplication;
import com.example.banksystem.models.Admin;
import com.example.banksystem.models.BankOfficer;
import com.example.banksystem.models.BankOfficerPrototype;
import com.example.banksystem.repositories.AdminRepository;
import com.example.banksystem.repositories.BankOfficerPrototypeRepository;
import com.example.banksystem.repositories.BankOfficerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    BankOfficerRepository bankOfficerRepository;

    @Autowired
    BankOfficerPrototypeRepository bankOfficerPrototypeRepository;

    /**
     * Admin's home page
     * @return home-adm template
     */
    @GetMapping("/adm")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminRepository.findByName(authentication.getName());
        model.addAttribute("user", admin);
        model.addAttribute("title", "Home page");
        return "admin/home-adm";
    }

    /**
     * Page of the confirmation of bank officer registration
     * @param name bank officer's username
     * @return response template
     */
    @GetMapping("/adm/add-bank-officer/{name}")
    public String response(Model model, @PathVariable(name = "name") String name) {
        BankOfficerPrototype bankOfficerPrototype = bankOfficerPrototypeRepository.findByUsername(name);

        model.addAttribute("bankOfficer", bankOfficerPrototype);
        model.addAttribute("title", "Response");
        return "admin/response";
    }

    /**
     * Process the admin response
     * @param response true if request was accepted false if was rejected
     * @return redirects to success or reject page
     */
    @GetMapping("/adm/add-bank-officer/{name}/process")
    public String response2(@PathVariable(name = "name") String name, @RequestParam boolean response) {
        if (response) {
            BankOfficerPrototype bankOfficerPrototype = bankOfficerPrototypeRepository.findByUsername(name);
            BankOfficer bo = new BankOfficer(bankOfficerPrototype.getUsername(), bankOfficerPrototype.getPassword());
            bankOfficerRepository.save(bo);

            Thread restartThread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    BankSystemApplication.restart();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            bankOfficerPrototypeRepository.delete(bankOfficerPrototype);

            restartThread.setDaemon(false);
            restartThread.start();

            return "redirect:/adm/add-bank-officer-success/" + bo.getId();
        } else {
            return "redirect:/adm/add-bank-officer-reject/" + name;
        }
    }

    /**
     * Page confirms that bank officer was successfully added
     * @param id id of bank officer
     * @return success template
     */
    @GetMapping("/adm/add-bank-officer-success/{id}")
    public String addBankOfficerSuccess(Model model, @PathVariable(name = "id") long id) {
        model.addAttribute("id", id);
        model.addAttribute("title", "Success");
        return "admin/success";
    }

    /**
     * Page confirms that bank officer registration was successfully rejected
     * @param name username of bank officer
     * @return reject template
     */
    @GetMapping("/adm/add-bank-officer-reject/{name}")
    public String addBankOfficerReject(Model model, @PathVariable(name = "name") String name) {
        BankOfficerPrototype bankOfficerPrototype = bankOfficerPrototypeRepository.findByUsername(name);
        bankOfficerPrototypeRepository.delete(bankOfficerPrototype);
        model.addAttribute("title", "Reject");
        return "admin/reject";
    }
}
