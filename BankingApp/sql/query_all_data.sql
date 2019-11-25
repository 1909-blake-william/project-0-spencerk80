SELECT users.name, users.passwd, accounts.acc_t, accounts.BAL, transactions.trn_t, transactions.bal AS amount, transactions.t_stamp
FROM users INNER JOIN accounts
ON users.id = accounts.owner INNER JOIN transactions
ON accounts.id = transactions.acc
WHERE accounts.owner != -1;