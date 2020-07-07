package com.circle.core;

import com.circle.data.Account;
import com.circle.data.Transaction;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class DBManager {
    private final static Logger LOGGER = LoggerFactory.getLogger(DBManager.class);
    private final DBI dbi;

    public DBManager(DBI dbi) {
        this.dbi = dbi;
    }

    public Optional<Account> getAccount(String id) {
        String query = "SELECT * from account WHERE accountid = :id";
        Handle handle = dbi.open();
        Account account = null;
        try {
            Map<String, Object> map = handle.createQuery(query).bind("id", id).first();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            account = objectMapper.convertValue(map, Account.class);
            handle.close();
        } catch (Exception e) {
            LOGGER.error("Unable to find account, pleases check the account id");
        }
        return Optional.ofNullable(account);
    }

    public void addNewAccount(Account a) {
        LOGGER.info("Writing new account [{}] to DB", a);
        String query = "INSERT INTO account(accountid, balance, createdOn) VALUES (?,?,?)";
        dbi.useHandle(handle -> handle.execute(query, a.getAccountId(), a.getBalance(), a.getCreatedOn()));
    }

    public void addNewTransaction(Transaction t) {
        LOGGER.info("Writing new transaction [{}] to DB", t);
        String query = "INSERT INTO transaction(transactionid, amount, receiver, sender, timestamp) VALUES(?, ?, ?, ?, ?)";
        dbi.useHandle(handle -> handle.execute(query, t.getTransactionID(), t.getAmount(), t.getReceiver().getAccountId(), t.getSender().getAccountId(), t.getTimestamp())
        );
    }

    public void updateAccountInfo(Account account) {
        LOGGER.info("Updating account [{}] balance [{}] to DB", account.getAccountId(), account.getBalance());
        String query = "UPDATE account SET balance = ? WHERE accountid = ?";
        dbi.useHandle(handle -> handle.execute(query, account.getBalance(), account.getAccountId()));
    }
}
