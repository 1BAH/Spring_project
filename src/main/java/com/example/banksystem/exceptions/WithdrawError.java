package com.example.banksystem.exceptions;

public class WithdrawError extends Exception {
    long accountId;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public WithdrawError (String message, long accountId) {
        super(message);
        this.accountId = accountId;
    }
}
