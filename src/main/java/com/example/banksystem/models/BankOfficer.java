package com.example.banksystem.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class BankOfficer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "bankOfficer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Bank> banks;

    private String username;

    private String password;
    @OneToMany(mappedBy = "bankOfficer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Account> accountsToRemove;

    public BankOfficer() {
    }

    public BankOfficer(long id, String username, String password) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.banks = new ArrayList<>();
        this.accountsToRemove = new ArrayList<>();
    }

    public BankOfficer(String username, String password) {
        this.password = password;
        this.username = username;
        this.banks = new ArrayList<>();
        this.accountsToRemove = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }

    public void addBank(Bank bank) {
        banks.add(bank);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getAccountsToRemove() {
        return accountsToRemove;
    }

    public void setAccountsToRemove(List<Account> accountsToRemove) {
        this.accountsToRemove = accountsToRemove;
    }

    public void addAccountToRemove(Account account) {
        accountsToRemove.add(account);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankOfficer that = (BankOfficer) o;
        return id == that.id && Objects.equals(banks, that.banks) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }
}
