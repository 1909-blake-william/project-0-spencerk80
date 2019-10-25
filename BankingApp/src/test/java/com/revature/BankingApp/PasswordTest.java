package com.revature.BankingApp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.BankingApp.util.Password;

public class PasswordTest {

	@Test
	public void hashTest() {
		
		String	password = "Cat 123 bob Jim!@#",
				hash;
		
		hash = Password.transformPasswd(password, "Kris");
		
		assertTrue(password.equals(Password.transformPasswd(hash, "Kris")));
		
	}
	
	@Test
	public void testValidPasswd() {
		
		assertTrue(Password.isSecure("Jim123 is bpppt"));
		
	}
	
	@Test
	public void testShortPasswd() {
		
		assertFalse(Password.isSecure("Li23"));
		
	}
	
	@Test
	public void testNumberFirst() {
		
		assertFalse(Password.isSecure("123Bobnaofwundo"));
		
	}
	
	@Test
	public void hasNoNumber() {
		
		assertFalse(Password.isSecure("Jimaoufntanud"));
		
	}
	
}
