create table transaction(
    transactionId uuid primary key,
    amount int,
    receiver varchar references account(accountID),
    sender varchar references account(accountID),
    timestamp timestamp not null
);