package com.circle.data;

import org.joda.time.DateTime;


public class Account {
    private String accountId;
    private int balance;
    private DateTime createdOn;

    public Account(String accountId, int balance) {
        this.accountId = accountId;
        this.balance = balance;
        this.createdOn = DateTime.now();
    }

    public Account() {

    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public DateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }
}
