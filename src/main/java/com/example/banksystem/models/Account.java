package com.example.banksystem.models;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * Account entity class with <b>id</b>, <b>amount</b>, <b>type</b>, <b>bank</b> and <b>holder</b> properties.
 */
@Entity
public class Account {
    private static final BigDecimal zero = new BigDecimal(0);

    /**
     * Account's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Account's amount
     */
    private BigDecimal amount;

    private byte alert;

    private long lastTransactionId;

    /**
     * Account's type
     */
    private String type;

    /**
     * Account's bank
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank", nullable = false)
    private Bank bank;

    /**
     * Account's holder
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account", nullable = false)
    private Client holder;

    public Account() {}

    /**
     * Constructor of Account objects of four parameters
     * @param amount - the amount of money that is contained in the account
     * @param type - the type of the account
     * @param bank - the bank where the account is located
     * @param holder - whose account
     */
    public Account(BigDecimal amount, String type, Bank bank, Client holder) {
        this.amount = amount;
        this.type = type;
        this.bank = bank;
        this.holder = holder;
        this.alert = 0;
    }

    /**
     * Constructor of Account objects of five parameters
     * @param id
     * @param amount - the amount of money that is contained in the account
     * @param type - the type of the account
     * @param bank - the bank where the account is located
     * @param holder - whose account
     */
    public Account(long id, BigDecimal amount, String type, Bank bank, Client holder) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.bank = bank;
        this.holder = holder;
        this.alert = 0;
    }

    /**
     * Set account id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set account's amount of money that is contained in the account
     * @param amount - amount of money that is contained in the account
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Set the type of the account
     * @param type - the type of the account
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get bank id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get account's amount of money that is contained in the account
     * @return amount of money that is contained in the account
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Get the type of the account
     * @return type of the account
     */
    public String getType() {
        return type;
    }


    /**
     * This method withdraws money from the account with a commission
     * @param amount1 - the amount of money that is withdrawn
     * @param commission - commission assigned by the bank
     * @return true if the account is credit or the account balance is not less than zero, false  otherwise
     */
    public boolean withdrawMoney(BigDecimal amount1, boolean commission) {
        BigDecimal amount2 = getAmount();

        if (commission) {
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

    /**
     * This method adds the new money deposited to the money already in the account
     * @param amount1 - the amount of money that is deposited
     */
    public void putMoney(BigDecimal amount1) {
        BigDecimal amount2 = getAmount();
        this.amount = amount2.add(amount1);
    }

    /**
     * This method accrues percentages on the amount
     * @param percentage bank rate
     */
    public void percents(float percentage) {
        if (type.equals("Credit") && (zero.compareTo(amount) > 0)) {
            setAlert((byte) 2);
            BigDecimal amount2 = getAmount();
            amount = amount2.multiply(new BigDecimal(String.valueOf(1 + percentage / 100)));
        }
    }

    /**
     * Get bank where the account is located
     * @return bank where the account is located
     */
    public Bank getBank() {
        return bank;
    }

    /**
     * Set bank where the account is located
     * @param bank - the bank where the account is located
     */
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public byte getAlert() {
        return alert;
    }

    public void setAlert(byte alert) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        this.alert = alert;
    }

    public long getLastTransactionId() {
        return lastTransactionId;
    }

    public void setLastTransactionId(long lastTransactionId) {
        this.lastTransactionId = lastTransactionId;
    }

    public Client getHolder() {
        return holder;
    }

    public void setHolder(Client holder) {
        this.holder = holder;
    }
}
