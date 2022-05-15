package com.example.banksystem.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Bank entity class with <b>id</b>, <b>name</b>, <b>percentage</b> and <b>accounts</b> properties.
 */
@Entity
public class Bank {

    /**
     * Bank's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Bank's name
     */
    private String name;

    /**
     * Bank's percentage
     */
    private float percentage;

    /**
     * Bank's accounts - all accounts in Bank
     */
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Account> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Constructor of Bank objects of two parameters
     * @param name - bank's name
     * @param percentage - what percentage does the bank give money
     */
    public Bank(String name, float percentage) {
        this.name = name;
        this.percentage = percentage;
        this.accounts = new ArrayList<>();
    }

    /**
     * Constructor of Bank objects of three parameters
     * @param id
     * @param name - bank's name
     * @param percentage - what percentage does the bank give money
     */
    public Bank(long id, String name, float percentage) {
        this.id = id;
        this.name = name;
        this.percentage = percentage;
        this.accounts = new ArrayList<>();
    }

    /**
     * Get bank id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set bank id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get bank's name
     * @return name of bank
     */
    public String getName() {
        return name;
    }

    /**
     * Set bank's name
     * @param name - bank's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get percentage with which does the bank give money
     * @return percentage with which does the bank give money
     */
    public float getPercentage() {
        return percentage;
    }

    /**
     * Set percentage with which does the bank give money
     * @param percentage - with which does the bank give money
     */
    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    /**
     * Get bank accounts
     * @return accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Set bank accounts
     * @param accounts in the bank
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * This method add account to existing ones
     * @param account - account in the bank
     */
    public void addAccounts(Account account) {
        accounts.add(account);
    }
}
