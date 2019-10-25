package com.revature.BankingApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.BankingApp.enums.TransactionType;
import com.revature.BankingApp.model.Transaction;
import com.revature.BankingApp.model.User;

public class AccountCreationTest {
	
	@Test
	public void createCheckingAccount() {
		
		User user = new User("Kris", "123");
		
		try {
		
			user.openChecking(20);
			
		//should not be reached
		} catch(Exception e) {
			
			assertTrue(false);
			
		}
		
		assertEquals(20.0, user.getChecking().getBalance(), 0.01);
		
	}
	
	@Test
	public void createSavingsAccount() {
		
		User user = new User("Kris", "123");
		
		try {
		
			user.openSaving(20);
			
		//Should not be reached
		} catch(Exception e) {
			
			assertTrue(false);
			
		}
		
		assertEquals(20.0, user.getSaving().getBalance(), 0.01);
		
	}
	
	@Test
	public void createCheckingAccountNegativeStartingAmount() {
		
		User user = new User("Kris", "123");
		
		try {
			
			user.openChecking(-5);
			assertTrue(false); //Should not be reached
			
		//Expected to reach
		} catch(Exception e) {
			
			assertTrue(true);
			
		}
		
	}
	
	@Test
	public void createSavingsAccountNegativeStartingAmount() {
		
		User user = new User("Kris", "123");
		
		try{
			
			
			user.openSaving(-5);
			assertTrue(false); //Should not reach
			
		//Expected to reach
		} catch(Exception e) {
			
			assertTrue(true);
			
		}
		
	}
	
	@Test
	public void	createAccountTestTransaction() {
		
		User 		user 	= new User("Kris", "123");
		Transaction	trans;
		
		try{
			
			user.openChecking(30);
			
		//Should not be reached
		} catch(Exception e) {
			
			assertTrue(false);
			
		}
		
		trans = user.getChecking().getTransactions().get(0);
		
		assertTrue((trans.getAmount() == 30) && (trans.getTransType() == TransactionType.DEPOSIT));
		
	}

}
