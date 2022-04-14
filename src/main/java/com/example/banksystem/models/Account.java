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
    private Bank bank;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client holder;

    public Account() {}

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
}
