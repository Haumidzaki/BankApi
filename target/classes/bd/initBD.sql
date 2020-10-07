DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS clients;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE clients
(
    id   BIGINT DEFAULT global_seq.nextval PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE accounts
(
    id         BIGINT DEFAULT global_seq.nextval PRIMARY KEY,
    clients_id BIGINT  NOT NULL,
    number     VARCHAR NOT NULL,
    sum        DOUBLE  NOT NULL,
    currency   VARCHAR NOT NULL,
    FOREIGN KEY (clients_id) REFERENCES clients (id) ON DELETE CASCADE
);

CREATE TABLE cards
(
    id         BIGINT DEFAULT global_seq.nextval PRIMARY KEY,
    account_id BIGINT  NOT NULL,
    number     VARCHAR NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE
);