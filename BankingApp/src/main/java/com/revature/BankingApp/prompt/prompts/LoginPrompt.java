package com.revature.BankingApp.prompt.prompts;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.revature.BankingApp.doa.AdminDoa;
import com.revature.BankingApp.doa.UserDoa;
import com.revature.BankingApp.model.Admin;
import com.revature.BankingApp.model.User;
import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;
import com.revature.BankingApp.util.Password;

/**
 * A prompt that allows a user to make a new account or login
 * 
 * @author Kristoffer Spencer
 */
public class LoginPrompt implements Prompt {
	
	Scanner	scan	= new Scanner(System.in);

	/**
	 * Runs the prompt and returns the main menu prompt
	 */
	@Override
	public Prompt run() {
		
		String 		name, pswd;
		User		user;
		Admin		admin		= null;
		
		name = getName();
		pswd = getPasswd();
		
		if(isNewUser(name))
			
			return Prompts.mkUser;
		
		if(AdminDoa.doa.adminExists(name)) {
			
			try {
			
				admin = AdminDoa.doa.getAdminInfo(name);
			
			} catch(Exception e) {
				
				//Don't do anything, program won't crash
			
			} finally {
				
				if(admin != null && admin.getPassword().equals(pswd))
					
					return Prompts.adminMenu;
				
			}
			
		}
			
		user = readUser(name);
		
		if(user == null || !user.getPassword().equals(pswd)) {
			
			System.err.println("\n\nInvalid login\n\n");
			return Prompts.login;
			
		}
		
		Prompts.menu.passUser(user);
		
		return Prompts.menu;
		
	}
	
	private boolean isNewUser(String name) {
		
		String	response;
		
		if(!UserDoa.doa.userExists(name)) {
			
			if(AdminDoa.doa.adminExists(name)) 
				
				return false;
			
			do {
				
				System.out.println("Account doesn't exist. Would you like to make a new account?");
				response = scan.nextLine();
				
			} while(!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no"));
			
			if(response.equalsIgnoreCase("yes"))
				
				return true;
			
			else return false;
			
		} else return false;
		
	}
	
	private String getName() {
		
		String name;
		
		System.out.println("\nEnter username:");
		name = scan.nextLine().trim();
		
		while(name.equals("")) {
			
			System.err.println("Error: Invalid name given\n");
			System.out.println("Try again: ");
			name = scan.nextLine().trim();
			
		}
		
		return name;
		
	}
	
	private String getPasswd() {
		
		String password;
		
		System.out.println("\nEnter password:");
		password = scan.nextLine().trim();
		
		while(password.equals("")) {
			
			System.err.println("Error: Invalid password given\n");
			System.out.println("Try again: ");
			password = scan.nextLine().trim();
			
		}
		
		return password;
		
	}
	
	private User readUser(String name) {
		
		try{
			
			return UserDoa.doa.getUserInfo(name);
			
		} catch(IOException ioe) {
			
			return null;
			
		}
		
	}

}
