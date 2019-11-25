package com.revature.BankingApp.prompt.prompts;

import java.util.Scanner;

import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;
import com.revature.BankingApp.util.SafeParser;

/**
 * A prompt used to navigate an admin around
 * 
 * @author Kristoffer Spencer
 */
public class AdminMenuPrompt implements Prompt {

	@Override
	public Prompt run() {
		
		int prompt = 0;
		
		printMenu();
		prompt = getInput();
		
		return goToPrompt(prompt);
		
	}
	
	private void printMenu() {
		
		System.out.println("\n--Administrator funtions--");
		System.out.println("1. View all users");
		System.out.println("2. View all accounts");
		System.out.println("3. Create a new admin account");
		System.out.println("4. Logout of the system");
		System.out.println("5. Shutdown the system");
		
	}
	
	private int getInput() {
		
		Scanner	scan		= new Scanner(System.in);
		int 	response;
		
		System.out.println("Please enter where you would like to go");
		response = SafeParser.parseInt(scan.nextLine());
		
		while(response < 1 || response > 5) {
			
			System.err.println("\nError: Invalid selection\n");
			System.out.println("Please enter where you would like to go");
			response = SafeParser.parseInt(scan.nextLine());
			
		}
		
		System.out.println();
		
		return response;
		
	}
	
	private Prompt goToPrompt(int prompt) {
		
		switch(prompt) {
		
			case 1:
				
				return Prompts.allUsers;
				
			case 2:
				
				return Prompts.allAccounts;
				
			case 3:
				
				return Prompts.mkAdmin;
				
			case 4:
				
				return Prompts.logout;
				
			case 5:
				
				return Prompts.shutdown;
		
		}
		
		return Prompts.adminMenu;
		
	}

}
