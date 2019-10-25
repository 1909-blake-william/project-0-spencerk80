package com.revature.BankingApp.doa;

import java.sql.SQLException;
import java.util.List;

import com.revature.BankingApp.enums.AccountType;
import com.revature.BankingApp.model.Transaction;
import com.revature.BankingApp.model.User;

public interface TransactionDoa {
	
	public static final TransactionDoa	doa		= new TransactionDoaSql();

	void saveTransactions(User user) throws SQLException;
	List<Transaction> getTransactions(User user, AccountType account) throws SQLException;
	
}
