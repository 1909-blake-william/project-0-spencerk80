package com.revature.BankingApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.revature.BankingApp.enums.AccountType;
import com.revature.BankingApp.enums.TransactionType;
import com.revature.BankingApp.model.Account;
import com.revature.BankingApp.model.Transaction;

public class AccountAndTransTest {

	private Account	acc	= new Account(AccountType.SAVINGS, 20.5);
	
	@Test
	public void createAccountTest() {
		
		assertTrue((acc.getBalance() == 20.5) && (acc.getTransactions().size() == 1));
		
	}
	
	@Test
	public void depositTest() {
		
		acc.deposit(30.5);
		assertEquals(51.0, acc.getBalance(), 0.01);
		
	}
	
	@Test
	public void transactionRecordTest() {
		
		Transaction	trans;
		
		acc.deposit(30.5);
		trans = acc.getTransactions().get(acc.getTransactions().size() - 1);
		assertTrue((Math.floor(trans.getAmount()) == 30) 
				&& (trans.getTransType() == TransactionType.DEPOSIT));
		
	}
	
	@Test
	public void withdrawTest() {
		
		acc.deposit(30.5);
		
		try {
		
			acc.withdraw(10.2);
			assertEquals(40.8, acc.getBalance(), 0.01);
			
		//Basically if this is reached, the account thought the balance
		//was gonna go negative. This should not happen at this part of
		//testing
		} catch(Exception e) {
			
			assertTrue(false);
			
		}
		
	}
	
	@Test
	public void overdrawSavingsTest() {
		
		try {
			
			acc.withdraw(500);
			//Should not be reached
			assertTrue(false);
			
		} catch(Exception e) {
			
			assertTrue(true);
			
		}
		
	}
	
	@Test
	public void overdrawCheckingTest() {
		
		acc = new Account(AccountType.CHECKING, 20.5);
		
		try {
		
			acc.withdraw(25.5);
			assertEquals(-5.0, acc.getBalance(), 0.01);
			
		//Should not be reached
		} catch (Exception e) {
			
			assertTrue(false);
			
		}
		
	}

}
