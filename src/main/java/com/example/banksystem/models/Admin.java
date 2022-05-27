package com.example.banksystem.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Admin entity class with <b>id</b>, <b>bankOfficers</b> and <b>name</b> properties.
 */
@Entity
public class Admin {

    /**
     * Admin's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Bank officers to be registered
     */
    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BankOfficerPrototype> bankOfficers;

    /**
     * Admin's name
     */
    private String name;

    /**
     * Constructor of Admin objects of one parameter
     * @param name - name of admin
     */
    public Admin(String name) {
        this.bankOfficers = new ArrayList<>();
        this.name = name;
    }

    public Admin() {
    }

    /**
     * Get all bank officers of this admin
     * @return bankOfficers of this admin
     */
    public List<BankOfficerPrototype> getBankOfficers() {
        return bankOfficers;
    }

    /**
     * Set all bank officers of this admin
     * @param bankOfficers of this admin
     */
    public void setBankOfficers(List<BankOfficerPrototype> bankOfficers) {
        this.bankOfficers = bankOfficers;
    }

    /**
     * This method adds new bank officer to the registration list
     * @param bankOfficer - new bank officer that adds to the registration list
     */
    public void addBankOfficer (BankOfficerPrototype bankOfficer) {
        bankOfficers.add(bankOfficer);
    }
}
