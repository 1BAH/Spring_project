package com.example.banksystem.models;

import javax.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float amount;

    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank", nullable = false)
    private Bank bank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account", nullable = false)
    private Client holder;

    public Account() {}

    public Account(float amount, String type, Bank bank, Client holder) {
        this.amount = amount;
        this.type = type;
        this.bank = bank;
        this.holder = holder;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Float getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public void withdrawMoney(float amount) {
        this.amount -= amount;
    }
}
