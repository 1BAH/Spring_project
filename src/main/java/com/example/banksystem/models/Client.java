package com.example.banksystem.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Client entity class with <b>id</b>, <b>name</b>, <b>surname</b>, <b>address</b>, <b>passport</b>, <b>accounts</b>, <b>transactionsFromThisAccount</b> and <b>transactionsToThisAccount</b> properties.
 */
@Entity
public class Client {

    /**
     * Client's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Client's name
     */
    private String name;

    /**
     * Client's surname
     */
    private String surname;

    /**
     * Client's address
     */
    private String address;

    /**
     * Client's passport
     */
    private String passport;

    /**
     * All accounts of client
     */
    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    /**
     * Client's transactionsFromThisAccount - all transactions made from this account
     */
    @OneToMany(mappedBy = "accountFrom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactionsFromThisAccount;

    /**
     * Client's transactionsToThisAccount - all transactions sent to this account
     */
    @OneToMany(mappedBy = "accountTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactionsToThisAccount;


    public Client() {
        this.accounts = new ArrayList<>();
        this.transactionsFromThisAccount = new ArrayList<>();
        this.transactionsToThisAccount = new ArrayList<>();
    }

    /**
     * Constructor of Client objects of five parameters
     * @param id
     * @param name - Client's name
     * @param surname - Client's surname
     * @param address - where Client lives
     * @param passport - Client's passport data
     */
    public Client(long id, String name, String surname, String address, String passport) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passport = passport;
        this.accounts = new ArrayList<>();
        this.transactionsToThisAccount = new ArrayList<>();
        this.transactionsFromThisAccount = new ArrayList<>();
    }

    /**
     * Get client id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set client id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get client's name
     * @return name of client
     */
    public String getName() {
        return name;
    }

    /**
     * Set client's name
     * @param name of client
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get client's surname
     * @return surname of client
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Get client's surname
     * @param surname of client
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Get address where client lives
     * @return address where client lives
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set address where client lives
     * @param address - where client lives
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get passport data of client
     * @return passport data of client
     */
    public String getPassport() {
        return passport;
    }

    /**
     * Set passport data of client
     * @param passport data of client
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }

    /**
     * Get all accounts
     * @return accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Set all accounts
     * @param accounts of client
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * This method add account to existing ones
     * @param account - account of client
     */
    public void addAccounts(Account account) {
        accounts.add(account);
    }
}
