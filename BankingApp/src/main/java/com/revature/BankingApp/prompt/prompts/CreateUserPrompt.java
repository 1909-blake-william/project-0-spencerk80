package com.revature.BankingApp.prompt.prompts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import com.revature.BankingApp.doa.AdminDoa;
import com.revature.BankingApp.doa.UserDoa;
import com.revature.BankingApp.model.Admin;
import com.revature.BankingApp.model.User;
import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;
import com.revature.BankingApp.util.Password;

/**
 * A prompt for setting up a new user at the bank
 * @author Kristoffer Spencer
 */
public class CreateUserPrompt implements Prompt {

	private Scanner	scan	= new Scanner(System.in);
	private String 	name;
	
	/**
	 * Runs the prompt and goes to the login prompt
	 */
	@Override
	public Prompt run() {
	
		User	user;
		String	password;
		
		printWelcome();
		
		do {
			
			if(this.name == null) this.name = getUserName();
			
			if(UserDoa.doa.userExists(name) || AdminDoa.doa.adminExists(name)) {
				
				System.err.println("\nError: User with that name already exists!\n");
				name = null;
				
			}
	
		} while(name == null);
		
		
		password = getUserPassword();
		user = new User(name, password);
		writeSaveInfo(user);
		printEndMsg();
		
		return Prompts.login;
		
	}
	
	private void printWelcome() {
		
		System.out.println("\n--Welcome to Revature Bank--");
		System.out.println("\nRunning user set up...\n");
		
	}
	
	private void printEndMsg() {
		
		System.out.println("\nThank you for regeristring an account.");
		System.out.println("Moving to the login screen\n\n");
		
	}
	
	private String getUserName() {
		
		String name;
		
		System.out.println("\nEnter the username to use:");
		name = scan.nextLine().trim();
		
		while(name.equals("")) {
			
			System.err.println("Error: Invalid name given\n");
			System.out.println("Try again: ");
			name = scan.nextLine().trim();
			
		}
		
		return name;
		
	}
	
	private String getUserPassword() {
		
		String password;
		
		System.out.println("\nEnter the password you would like to use:");
		password = scan.nextLine().trim();
		
		while(password.equals("") || !Password.isSecure(password)) {
			
			System.err.println("Error: Invalid password given\n");
			System.out.println("Try again: ");
			password = scan.nextLine().trim();
			
		}
		
		return password;
		
	}
	
	/**
	 * Attempts to save the user info
	 * @param user: The user obj
	 */
	private void writeSaveInfo(User user) {
		
		try {
			
			UserDoa.doa.saveUserInfo(user);
			
		} catch(IOException ioe) {
			
			System.err.println("\n\n" + ioe.getMessage());
			
		}
		
	}
	
	public void passName(String name) {
		
		this.name = name;
		
	}

}
