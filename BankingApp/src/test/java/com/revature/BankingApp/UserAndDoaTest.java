package com.revature.BankingApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.revature.BankingApp.doa.UserDoa;
import com.revature.BankingApp.model.User;

public class UserAndDoaTest {

	@Test
	public void testUsername_mkUser() {
		
		User user = new User("Kris", "123");
		
		assertEquals("Kris", user.toString());
		
	}
	
	@Test
	public void testPassword_mkUser() {
		
		User user = new User("Kris", "123");
		
		assertEquals("123", user.getPassword());
		
	}
	
	@Test
	public void saveUser() throws IOException {
		
		User 	user 	= new User("Kris", "123");
		File	file;
		
		UserDoa.doa.saveUserInfo(user);
		file = new File("save/user/Kris.save");
		
		assertTrue(file.exists());
		
	}
	
	@Test
	public void readUser() throws IOException {
		
		User		user	= new User("Kris", "123"),
					fromFile;
		
		fromFile = UserDoa.doa.getUserInfo("Kris");
		
		assertEquals(user, fromFile);
		
	}
	
}
