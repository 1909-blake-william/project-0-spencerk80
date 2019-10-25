package com.revature.BankingApp.prompt.prompts;

import java.util.Scanner;

import com.revature.BankingApp.enums.AccountType;
import com.revature.BankingApp.exceptions.AccountAlreadyExistsException;
import com.revature.BankingApp.exceptions.AccountHasBalanceException;
import com.revature.BankingApp.exceptions.OpenWithNegBalException;
import com.revature.BankingApp.model.User;
import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;
import com.revature.BankingApp.util.SafeParser;

public class CloseAccountPrompt implements Prompt {

	User user;

	/**
	 * Run the prompt
	 */
	@Override
	public Prompt run() {

		closeAccount(getAccountType());
		
		return Prompts.menu;
		
	}
	
	public void passUser(User user) {
		
		this.user = user;
		
	}
	
	private void closeAccount(AccountType accType) {
		
		if(accType == AccountType.CHECKING)
			
			if(user.getChecking() == null)
				
				System.err.println("\n\nNo such account exists\n\n");
		
			else
				
				try {
					
					user.closeChecking();
					
				} catch(AccountHasBalanceException e) {
					
					System.err.println("\n\nCannot close account with a balance");
					
				}
		
		else
			
			if(user.getSaving() == null)
				
				System.err.println("\n\nNo such account exists\n\n");
		
			else
				
				try {
					
					user.closeSaving();
					
				} catch(AccountHasBalanceException e) {
					
					System.err.println("\n\nCannot close account with a balance\n\n");
					
				}
		
	}
	
	private AccountType getAccountType() {
		
		Scanner		scan		= new Scanner(System.in);
		AccountType	type;
		
		do {
		
			System.out.println("Which type of account would you like to close?");
			System.out.println("Checking or savings?");
			type = SafeParser.parseAccountType(scan.nextLine());
			
			if(type == null) System.err.println("\n\nError: Invalid selection\n\n");
			
		} while(type == null);

		return type;
		
	}

}
