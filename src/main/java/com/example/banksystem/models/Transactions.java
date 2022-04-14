package com.example.banksystem.models;

import javax.persistence.*;

@Entity
@Table(name = "Transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Accounts accountFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    private Accounts accountTo;

    private float amount;

    public Transactions() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accounts getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Accounts accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Accounts getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Accounts accountTo) {
        this.accountTo = accountTo;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
