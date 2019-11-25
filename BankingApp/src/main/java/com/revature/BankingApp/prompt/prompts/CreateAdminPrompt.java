package com.revature.BankingApp.prompt.prompts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import com.revature.BankingApp.doa.AdminDoa;
import com.revature.BankingApp.doa.UserDoa;
import com.revature.BankingApp.model.Admin;
import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;
import com.revature.BankingApp.util.Password;

/**
 * A prompt run only if the admin info is missing, which is basically
 * the system set up here. This simply gets the data for the account
 * 
 * @author Kristoffer Spencer
 */
public class CreateAdminPrompt implements Prompt {
	
	private Scanner	scan	= new Scanner(System.in);
	
	/**
	 * Runs the prompt and return the login prompt
	 */
	public Prompt run() {
		
		Admin	admin;
		String	name,
				password;
		
		printWelcome();
		
		do {
			
			name = getAdminName();
			
			if(UserDoa.doa.userExists(name) || AdminDoa.doa.adminExists(name)) {
				
				System.err.println("\nError: User with that name already exists!\n");
				name = null;
				
			}
	
		} while(name == null);
		
		password = getAdminPassword();
		admin = new Admin(name, password);
		writeSaveInfo(admin);
		printEndMsg();
		
		return Prompts.login;
		
	}
	
	private void printWelcome() {
		
		System.out.println("--Welcome to Revature Bank--");
		
	}
	
	private void printEndMsg() {
		
		System.out.println("\nThank you for setting up the admin account.");
		System.out.println("Moving to the login screen\n\n");
		
	}
	
	private String getAdminName() {
		
		String name;
		
		System.out.println("\nEnter the username for the admin:");
		name = scan.nextLine().trim();
		
		while(name.equals("")) {
			
			System.err.println("Error: Invalid name given\n");
			System.out.println("Try again: ");
			name = scan.nextLine().trim();
			
		}
		
		return name;
		
	}
	
	private String getAdminPassword() {
		
		String password;
		
		System.out.println("\nEnter the password for the admin:");
		password = scan.nextLine().trim();
		
		while(password.equals("") || !Password.isSecure(password)) {
			
			System.err.println("Error: Invalid password given\n");
			System.out.println("Try again: ");
			password = scan.nextLine().trim();
			
		}
		
		return password;
		
	}
	
	/**
	 * Attempts to save the admin info
	 * @param admin: The admin obj
	 */
	private void writeSaveInfo(Admin admin) {
		
		try {
			
			AdminDoa.doa.saveAdminInfo(admin);
			
		} catch(IOException ioe) {
			
			System.err.println("\nError: Could not update admin info to file");
			
		}
		
	}
	
}
