DELETE
FROM accounts;
DELETE
FROM clients;

INSERT INTO clients (name)
VALUES ('Vasay'), //100_000
       ('Petya');//100_001

INSERT INTO accounts (clients_id, number, sum, currency)
VALUES ('100000', '1111111111', '1000', 'RUB'), //100_002
       ('100001', '2222222222', '2000', 'RUB'); //100_003

INSERT INTO cards (account_id, number)
VALUES ('100002', '1234-1234-1234-1234'), // 100_004
       ('100003', '5555-4444-3333-2222'); // 100_005