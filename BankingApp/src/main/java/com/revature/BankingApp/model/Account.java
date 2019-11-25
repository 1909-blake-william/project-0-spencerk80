package com.revature.BankingApp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.revature.BankingApp.enums.AccountType;
import com.revature.BankingApp.enums.TransactionType;
import com.revature.BankingApp.exceptions.SavingAccountOverdraftException;

/**
 * Account representation for a user's checking or
 * savings account. Has a list of transactions and
 * basic info
 * 
 * @author Kristoffer Spencer
 */
public class Account implements Serializable {

	private static final long serialVersionUID = 4594253148760067137L;
	
	private 	List<Transaction>	transactions;
	private		double				balance;
	private		AccountType			accType;
	
	public Account() {
		
		this(AccountType.CHECKING, 0.0);
		
	}
	
	public Account(AccountType accT, double bal) {
		
		this.accType 		= accT;
		this.balance 		= bal;
		this.transactions	= new ArrayList<Transaction>();
		
		//If this is an account with no transactions and with a starting balance
		//Add a transaction signifying the opening of the account
		if(this.transactions.size() == 0 && bal > 0)
			
			this.transactions.add(new Transaction(TransactionType.DEPOSIT, bal));
		
	}
	
	public Account(AccountType accT, double bal, List<Transaction> trans) {
		
		this(accT, bal);
		this.transactions = trans;
		
		if(this.transactions == null)
			
			this.transactions = new ArrayList<Transaction>();
		
	}
	
	/**
	 * Deposit money into the account. Makes a new
	 * transaction to add to the list to record the
	 * record.
	 * @param amount Amount of money to add
	 */
	public void deposit(double amount) {
		
		Transaction	trans = new Transaction(TransactionType.DEPOSIT, amount);
		
		this.transactions.add(trans);
		this.balance += amount;
		
	}
	
	/**
	 * Withdraw money from account. Makes a new
	 * Transaction to add to the list to record the
	 * record. Also will not allow the withdraw if
	 * balance will go negative for a savings account
	 * @throws SavingAccountOverdraftException
	 * @param ammount of money to subtract
	 */
	public void withdraw(double amount) throws SavingAccountOverdraftException {
		
		Transaction	trans;
		
		if(this.accType == AccountType.SAVINGS && this.balance - amount < 0) 
			
			throw new SavingAccountOverdraftException("Transaction incomplete due to insufficient funds");
		
		trans = new Transaction(TransactionType.WITHDRAW, amount);
		this.transactions.add(trans);
		this.balance -= amount;
		
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public double getBalance() {
		return balance;
	}

	public AccountType getAccType() {
		return accType;
	}

	@Override
	public String toString() {

		return this.accType.toString();
		
	}
	
}
