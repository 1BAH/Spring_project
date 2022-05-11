package com.example.banksystem.models;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Transaction model
 */
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_from", nullable = false)
    private Account accountFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_to", nullable = false)
    private Account accountTo;

    private BigDecimal amount;

    public Transaction() {}

    public Transaction(Account accountFrom, Account accountTo, BigDecimal amount) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transaction(Long id, Account accountFrom, Account accountTo, BigDecimal amount) {
        this.id = id;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    /**
     * Get transaction id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the transaction id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the account where money were taken from
     * @return account where money were taken from
     */
    public Account getAccountFrom() {
        return accountFrom;
    }

    /**
     * Set the account where money were sent to
     * @param accountFrom - where money were sent to
     */
    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    /**
     * Get the account where money were sent to
     * @return account where money were sent to
     */
    public Account getAccountTo() {
        return accountTo;
    }

    /**
     * Set the account where money were sent to
     * @param accountTo - where money were sent to
     */
    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    /**
     * Get the amount of money that where sent
     * @return the amount of money that where sent
     */
    public BigDecimal getAmount() {
        return amount;
    }
    /**
     * Get the amount of money that where sent
     * @param amount - the amount of money that where sent
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
