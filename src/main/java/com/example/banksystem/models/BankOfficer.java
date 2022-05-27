package com.example.banksystem.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BankOfficer entity class with <b>id</b>, <b>username</b>, <b>banks</b>, <b>accountsToRemove</b> and <b>password</b> properties.
 */
@Entity
public class BankOfficer {

    /**
     * BankOfficer's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * BankOfficer's banks
     */
    @OneToMany(mappedBy = "bankOfficer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Bank> banks;

    /**
     * BankOfficer's username
     */
    private String username;

    /**
     * BankOfficer's password
     */
    private String password;

    /**
     * BankOfficer's accountsToRemove
     */
    @OneToMany(mappedBy = "bankOfficer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Account> accountsToRemove;

    public BankOfficer() {
    }

    /**
     * Constructor of BankOfficer objects of three parameters
     * @param id
     * @param username - name of bank officer
     * @param password - password for account of bank officer
     */
    public BankOfficer(long id, String username, String password) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.banks = new ArrayList<>();
        this.accountsToRemove = new ArrayList<>();
    }


    /**
     * Constructor of BankOfficer objects of three parameters
     * @param username - name of bank officer
     * @param password - password for account of bank officer
     */
    public BankOfficer(String username, String password) {
        this.password = password;
        this.username = username;
        this.banks = new ArrayList<>();
        this.accountsToRemove = new ArrayList<>();
    }

    /**
     * Get bankOfficer id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Set bankOfficer id
     * @param id of bank officer
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get banks that bank officer has
     * @return banks that bank officer has
     */
    public List<Bank> getBanks() {
        return banks;
    }

    /**
     * Set banks that bank officer has
     * @param banks - all banks that bank officer has
     */
    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }


    /**
     * This method adds a bank to the bank officer's existing ones
     * @param bank that needs to add to the bank officer's existing ones
     */
    public void addBank(Bank bank) {
        banks.add(bank);
    }

    /**
     * Get username of bank officer
     * @return username of bank officer
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username of bank officer
     * @param username of bank officer
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password for account of bank officer
     * @return password for account of bank office
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password for account of bank officer
     * @param password for account of bank officer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get accountsToRemove - all accounts to be deleted
     * @return accountsToRemove - all accounts to be deleted
     */
    public List<Account> getAccountsToRemove() {
        return accountsToRemove;
    }

    /**
     * Set accountsToRemove - all accounts to be deleted
     * @param accountsToRemove - all accounts to be deleted
     */
    public void setAccountsToRemove(List<Account> accountsToRemove) {
        this.accountsToRemove = accountsToRemove;
    }

    /**
     * This method adds account to accountsToRemove
     * @param account that adds to accountsToRemove
     */
    public void addAccountToRemove(Account account) {
        accountsToRemove.add(account);
    }


    /**
     * This method return true if objects equals or false otherwise
     * @param o object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankOfficer that = (BankOfficer) o;
        return id == that.id && Objects.equals(banks, that.banks) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }
}
