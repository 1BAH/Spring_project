package com.example.banksystem.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private float percentage;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Account> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    public Bank(String name, float percentage) {
        this.name = name;
        this.percentage = percentage;
        this.accounts = new ArrayList<>();
    }

    public Bank(long id, String name, float percentage) {
        this.id = id;
        this.name = name;
        this.percentage = percentage;
        this.accounts = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccounts(Account account) {
        accounts.add(account);
    }
}
