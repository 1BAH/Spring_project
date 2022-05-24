package com.example.banksystem.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BankOfficerPrototype> bankOfficers;

    private String name;

    public Admin(String name) {
        this.bankOfficers = new ArrayList<>();
        this.name = name;
    }

    public Admin() {
    }

    public List<BankOfficerPrototype> getBankOfficers() {
        return bankOfficers;
    }

    public void setBankOfficers(List<BankOfficerPrototype> bankOfficers) {
        this.bankOfficers = bankOfficers;
    }

    public void addBankOfficer (BankOfficerPrototype bankOfficer) {
        bankOfficers.add(bankOfficer);
    }
}
