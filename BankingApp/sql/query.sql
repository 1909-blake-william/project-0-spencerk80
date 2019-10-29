SELECT name, acc_t, accounts.bal, transactions.bal AS amount, transactions.trn_t
FROM users INNER JOIN accounts
ON accounts.owner = users.id INNER JOIN transactions
ON transactions.acc = accounts.id;