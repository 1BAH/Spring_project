package com.example.banksystem.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Account {
    private static BigDecimal zero = new BigDecimal(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank", nullable = false)
    private Bank bank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account", nullable = false)
    private Client holder;

    public Account() {}

    public Account(BigDecimal amount, String type, Bank bank, Client holder) {
        this.amount = amount;
        this.type = type;
        this.bank = bank;
        this.holder = holder;
    }

    public Account(long id, BigDecimal amount, String type, Bank bank, Client holder) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.bank = bank;
        this.holder = holder;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public boolean withdrawMoney(BigDecimal amount1, boolean commision) {
        BigDecimal amount2 = getAmount();

        if (commision) {
            amount1 = amount1.multiply(new BigDecimal(bank.getPercentage() / 100 + 1));
        }

        BigDecimal newAmount = amount2.subtract(amount1);


        if (type.equals("Credit") || (newAmount.compareTo(zero) >= 0)) {
            this.amount = newAmount;
            return true;
        } else {
            return false;
        }
    }

    public void putMoney(BigDecimal amount1) {
        BigDecimal amount2 = getAmount();
        this.amount = amount2.add(amount1);
    }

    public void percents(float percentage) {
        if (type.equals("Credit") && (zero.compareTo(amount) > 0)) {
            BigDecimal amount2 = getAmount();
            this.amount = amount2.multiply(new BigDecimal(String.valueOf(1 + percentage / 100)));
        }
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
