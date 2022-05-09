package com.example.banksystem.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String address;

    private String passport;

    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    @OneToMany(mappedBy = "accountFrom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactionsFromThisAccount;

    @OneToMany(mappedBy = "accountTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactionsToThisAccount;

    public Client() {
        this.accounts = new ArrayList<>();
        this.transactionsFromThisAccount = new ArrayList<>();
        this.transactionsToThisAccount = new ArrayList<>();
    }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
