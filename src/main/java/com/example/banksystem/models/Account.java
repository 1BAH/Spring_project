package com.example.banksystem.models;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * Account entity class with <b>id</b>, <b>amount</b>, <b>alert</b>, <b>lastTransactionId</b>, <b>type</b>, <b>bank</b> and <b>holder</b> properties.
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

    /**
     * Account's alert
     */
    private byte alert;

    /**
     * Account's lastTransactionId
     */
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankOfficer", nullable = true)
    private BankOfficer bankOfficer;

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
        this.bankOfficer = null;
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
        this.bankOfficer = null;
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

    /**
     * Get alert on the account
     * @return alert on the account
     */
    public byte getAlert() {
        return alert;
    }

    /**
     * Set alert on the account
     * @param alert on the account
     */
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

    /**
     * Get lastTransactionId - id transaction that was made last
     * @return lastTransactionId - id transaction that was made last
     */
    public long getLastTransactionId() {
        return lastTransactionId;
    }

    /**
     * Set lastTransactionId - id transaction that was made last
     * @param lastTransactionId - id transaction that was made last
     */
    public void setLastTransactionId(long lastTransactionId) {
        this.lastTransactionId = lastTransactionId;
    }

    /**
     * Get holder of this account
     * @return holder of this account
     */
    public Client getHolder() {
        return holder;
    }

    /**
     * Set holder of this account
     * @param holder of this account
     */
    public void setHolder(Client holder) {
        this.holder = holder;
    }

    public BankOfficer getBankOfficer() {
        return bankOfficer;
    }

    public void setBankOfficer(BankOfficer bankOfficer) {
        this.bankOfficer = bankOfficer;
    }
}
