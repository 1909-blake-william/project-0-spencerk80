package com.revature.BankingApp.prompt.prompts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.revature.BankingApp.doa.TransactionDoa;
import com.revature.BankingApp.doa.UserDoa;
import com.revature.BankingApp.model.Transaction;
import com.revature.BankingApp.model.User;
import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;
import com.revature.BankingApp.util.SafeParser;

/**
 * A prompt that's the main menu for a sure to navigate around with
 * 
 * @author Kristoffer Spencer
 */
public class MenuPrompt implements Prompt {
	
	private	User user;
	
	@Override
	public Prompt run() {
		
		int prompt = 0;
		
		//Pass around the user data to all the prompts that need it
		Prompts.openAccount.passUser(user);
		Prompts.closeAccount.passUser(user);
		Prompts.deposit.passUser(user);
		Prompts.withdraw.passUser(user);
		
		printMenu();
		prompt = getInput();
		
		return goToPrompt(prompt);
		
	}
	
	public void passUser(User user) {
		
		this.user = user;
		
	}
	
	private void printMenu() {
		
		System.out.println("\n--Main Menu--");
		System.out.println("1. View Accounts");
		System.out.println("2. View all transactions");
		System.out.println("3. Make a deposit");
		System.out.println("4. Make a withdraw");
		System.out.println("5. Open an account");
		System.out.println("6. Close an account");
		System.out.println("7. Logout of the system");
		
	}
	
	private int getInput() {
		
		Scanner	scan		= new Scanner(System.in);
		int 	response;
		
		System.out.println("Please enter where you would like to go");
		response = SafeParser.parseInt(scan.nextLine());
		
		while(response < 1 || response > 7) {
			
			System.err.println("\n\nError: Invalid selection\n\n");
			System.out.println("Please enter where you would like to go");
			response = SafeParser.parseInt(scan.nextLine());
			
		}
		
		System.out.println();
		
		return response;
		
	}
	
	private Prompt goToPrompt(int prompt) {
		
		switch(prompt) {
		
			case 1:
				
				printAccounts();
				return Prompts.menu;
				
			case 2:
				
				printTransactions();
				return Prompts.menu;
				
			case 3:
				
				return Prompts.deposit;
				
			case 4:
				
				return Prompts.withdraw;
				
			case 5:
				
				return Prompts.openAccount;
				
			case 6:
				
				return Prompts.closeAccount;
				
			case 7:
				
				try {
				
					UserDoa.doa.saveUserInfo(user);
					
				} catch(IOException ioe) {
					
					System.err.println(ioe.getMessage());
					
				}
				
				return Prompts.logout;
				
			default:
					
					return Prompts.menu;
		
		}
		
	}
	
	private void printAccounts() {
		
		if(user.getChecking() == null)
			
			System.out.println("No checking account found");
		
		else
			
			System.out.printf("\nChecking account balance: $%.2f\n", user.getChecking().getBalance());
		
		if(user.getSaving() == null) 
			
			System.out.println("No savings account found");
		
		else

			System.out.printf("\nSavings accounts balance: $%.2f\n", user.getSaving().getBalance());
		
	}
	
	private void printTransactions() {
		
		int		numAccounts = 0;
		
		if(user.getChecking() != null) 	numAccounts++;
		if(user.getSaving() != null) 	numAccounts++;
		
		try {
			
			//Save User data to write changes down first
			UserDoa.doa.saveUserInfo(user);
		
			if(numAccounts == 2) { //If user has both
				
				System.out.println("--Checking--\n");
				
				for(Transaction t : TransactionDoa.doa.getTransactions(user, user.getChecking().getAccType()))
					
					System.out.println(t.toString());
				
				System.out.println("\n--Savings--\n");
				
				for(Transaction t : TransactionDoa.doa.getTransactions(user, user.getSaving().getAccType()))
					
					System.out.println(t.toString());
				
			} else if(numAccounts == 1) //If user has one
				
				if(user.getChecking() != null)
					
					for(Transaction t : TransactionDoa.doa.getTransactions(user, user.getChecking().getAccType()))
						
						System.out.println(t.toString());
					
				else
					
					for(Transaction t : TransactionDoa.doa.getTransactions(user, user.getSaving().getAccType()))
						
						System.out.println(t.toString());
					
				
			else //user has neither
				
				System.out.println("You have no open accounts");
			
		} catch(Exception e) {
			
			System.err.println(e.getMessage());
			
		}
		
	}

}
