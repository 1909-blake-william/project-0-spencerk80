package com.revature.BankingApp.prompt.prompts;

import java.util.Scanner;

import com.revature.BankingApp.enums.AccountType;
import com.revature.BankingApp.exceptions.AccountAlreadyExistsException;
import com.revature.BankingApp.exceptions.OpenWithNegBalException;
import com.revature.BankingApp.model.User;
import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;
import com.revature.BankingApp.util.SafeParser;

/**
 * A prompt to allow a user to open an account
 * 
 * @author Kristoffer Spencer
 */
public class OpenAccountPrompt implements Prompt {
	
	User user;

	/**
	 * Run the prompt
	 */
	@Override
	public Prompt run() {

		openAccount(getAccountType(), getAmount());
		
		return Prompts.menu;
		
	}
	
	public void passUser(User user) {
		
		this.user = user;
		
	}
	
	private double getAmount() {
		
		Scanner	scan	= new Scanner(System.in);
		
		System.out.println("Enter the amount you'd like to open the account with");
		
		return SafeParser.parseDouble(scan.nextLine());
		
	}
	
	private void openAccount(AccountType accType, double amount) {
		
		try {
		
			if(accType == AccountType.CHECKING)
				
				user.openChecking(amount);
			
			else
				
				user.openSaving(amount);
		
		} catch(AccountAlreadyExistsException e) {
			
			System.err.println("\n\nError: Account already exists\n\n");
			
		} catch(OpenWithNegBalException e) {
			
			System.err.println("\n\nError: Cannot open with a negative balance!\n\n");
			
		}
		
	}
	
	private AccountType getAccountType() {
		
		Scanner		scan		= new Scanner(System.in);
		AccountType	type;
		
		do {
		
			System.out.println("Which type of account would you like to open?");
			System.out.println("Checking or savings?");
			type = SafeParser.parseAccountType(scan.nextLine());
			
			if(type == null) System.err.println("\n\nError: Invalid selection\n\n");
			
		} while(type == null);

		return type;
		
	}

}
