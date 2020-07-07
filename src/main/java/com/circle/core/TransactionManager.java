package com.circle.core;

import com.circle.data.Account;
import com.circle.data.Transaction;
import com.circle.exception.InsufficientBalanceException;

public class TransactionManager {
    private Account sender;
    private Account receiver;
    private int amount;

    public TransactionManager(Account sender, Account receiver, int amount) {
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Transaction makeTransaction() {
        if (amount > sender.getBalance()) {
            throw new InsufficientBalanceException("Insufficient balance!");
        }
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        return new Transaction(amount, sender, receiver);
    }

}
