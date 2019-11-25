package com.revature.BankingApp.model;

import java.io.Serializable;
import java.time.Instant;

import com.revature.BankingApp.enums.TransactionType;

/**
 * Models a transaction made on an account for the bank
 * 
 * @author Kristoffer Spencer
 */
public class Transaction implements Serializable {

	private static final long serialVersionUID = -1986056300132024065L;
	
	private		TransactionType	transType;
	private		double			amount;
	private		String			timestamp;
	
	public Transaction() {
		
		this(TransactionType.DEPOSIT, 0.0);
		
	}
	
	public Transaction(TransactionType transType, double amount) {
		
		this.transType = transType;
		this.amount = amount;
		this.timestamp = Instant.now().toString();
		
	}
	
	public Transaction(TransactionType transType, double amount, String stamp) {
		
		this.transType = transType;
		this.amount = amount;
		this.timestamp = stamp;
		
	}
	
	public TransactionType getTransType() {
		return transType;
	}

	public double getAmount() {
		return amount;
	}

	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Returns a readable sentence about the transaction
	 */
	@Override
		public String toString() {
			
			return String.format("%s for %.2f : %s", 
					this.transType, this.amount, this.timestamp.toString());
			
		}

}
