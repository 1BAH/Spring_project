package com.example.banksystem.controllers;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Bank;
import com.example.banksystem.models.BankOfficer;
import com.example.banksystem.models.Transaction;
import com.example.banksystem.repositories.AccountRepository;
import com.example.banksystem.repositories.BankOfficerRepository;
import com.example.banksystem.repositories.BankRepository;
import com.example.banksystem.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class BankOfficerController {
    @Autowired
    BankOfficerRepository bankOfficerRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/bo/bank-info/{id}")
    public String bankInfo(Model model, @PathVariable(name = "id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankOfficer bankOfficer = bankOfficerRepository.findByUsername(authentication.getName());
        Bank bank = bankRepository.findById(id).get();

        if (!bank.getBankOfficer().equals(bankOfficer)) {
            model.addAttribute("title", "ERROR");
            return "redirect:/bo/forbidden";
        } else {
            model.addAttribute("title", "Bank @" + id);
            model.addAttribute("bank", bank);
            return "banks/bank-info";
        }
    }

    @GetMapping("/bo/bank-change-info/form/{id}")
    public String bankChangeInfo(@PathVariable(name = "id") long id, @RequestParam String name, @RequestParam float percentage, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankOfficer bankOfficer = bankOfficerRepository.findByUsername(authentication.getName());

        if (Objects.isNull(bankOfficer)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-bo";
        }

        Bank bank = bankRepository.findById(id).get();
        bank.setName(name);
        bank.setPercentage(percentage);
        bankRepository.save(bank);
        return "redirect:/bo/bank-info/" + id;
    }

    @GetMapping("/bo/bank-change-info/{id}")
    public String bankChangeInfoForm(Model model, @PathVariable(name = "id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankOfficer bankOfficer = bankOfficerRepository.findByUsername(authentication.getName());

        if (Objects.isNull(bankOfficer)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-bo";
        }

        Bank bank = bankRepository.findById(id).get();

        if (!bank.getBankOfficer().equals(bankOfficer)) {
            model.addAttribute("title", "ERROR");
            return "redirect:/bo/forbidden";
        } else {
            model.addAttribute("title", "Change information");
            model.addAttribute("bank", bank);
            return "banks/bank-change";
        }
    }

    @GetMapping("/bo/forbidden")
    public String forbidden(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankOfficer bankOfficer = bankOfficerRepository.findByUsername(authentication.getName());

        if (Objects.isNull(bankOfficer)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-bo";
        }

        model.addAttribute("title", "ERROR");
        return "errors/restricted-bo";
    }

    @GetMapping("/bo/account-delete/{id}")
    public String tryDeleteAccount(@PathVariable(name = "id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankOfficer bankOfficer = bankOfficerRepository.findByUsername(authentication.getName());

        if (Objects.isNull(bankOfficer)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-bo";
        }

        Account account = accountRepository.findById(id).get();
        model.addAttribute("title", "Respond to request");
        model.addAttribute("acc", account);
        return "bankOff/delete-bo";
    }

    @GetMapping("/bo/account-delete/{id}/process")
    public String deleteAccount(@PathVariable(name = "id") long id, @RequestParam boolean response, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankOfficer bankOfficer = bankOfficerRepository.findByUsername(authentication.getName());

        if (Objects.isNull(bankOfficer)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-bo";
        }

        if (response) {
            Account account = accountRepository.findById(id).get();

            List<Transaction> transactions = transactionRepository.findAllByAccountTo(account);

            for (Transaction transaction: transactions) {
                transactionRepository.delete(transaction);
            }

            transactions = transactionRepository.findAllByAccountFrom(account);

            for (Transaction transaction: transactions) {
                transactionRepository.delete(transaction);
            }

            accountRepository.delete(account);
            return "redirect:/bo/account-delete-success/" + id;
        } else {
            Account account = accountRepository.findById(id).get();
            account.setBankOfficer(null);
            account.setAlert((byte) 3);
            accountRepository.save(account);
            return "redirect:/bo/account-delete-reject/" + id;
        }
    }

    @GetMapping("/bo/account-delete-success/{id}")
    public String deleteAccountSuccess(Model model, @PathVariable(name = "id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankOfficer bankOfficer = bankOfficerRepository.findByUsername(authentication.getName());

        if (Objects.isNull(bankOfficer)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-bo";
        }

        model.addAttribute("id", id);
        model.addAttribute("title", "Success");
        return "bankOff/delete-success";
    }

    @GetMapping("/bo/account-delete-reject/{id}")
    public String deleteAccountReject(Model model, @PathVariable(name = "id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankOfficer bankOfficer = bankOfficerRepository.findByUsername(authentication.getName());

        if (Objects.isNull(bankOfficer)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-bo";
        }

        model.addAttribute("id", id);
        model.addAttribute("title", "Reject");
        return "bankOff/delete-reject";
    }
}
