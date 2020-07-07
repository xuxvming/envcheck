create table account(
    accountId varchar primary key,
    balance int default 0,
    createdOn timestamp not null
);
