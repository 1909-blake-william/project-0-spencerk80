package com.revature.BankingApp.prompt.prompts;

import java.util.Scanner;

import com.revature.BankingApp.enums.AccountType;
import com.revature.BankingApp.exceptions.SavingAccountOverdraftException;
import com.revature.BankingApp.model.User;
import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;
import com.revature.BankingApp.util.SafeParser;

public class MakeWithdrawPrompt implements Prompt {

	User user;
	
	@Override
	public Prompt run() {

		AccountType type = getAccountType();
		
		if(type == AccountType.CHECKING)
			
			try {
				
				if(user.getChecking() == null) {
					
					System.err.println("\nError: No such account\n");
					return Prompts.menu;
					
				}
				
				user.getChecking().withdraw(getAmount());
				
			} catch (SavingAccountOverdraftException e1) {

				System.err.println("\nError: Overdraft should be allowed on checking acocunt. See programmer\n");
				
			}
		
		else
			
			try {
				
				if(user.getSaving() == null) {
					
					System.err.println("\nError: No such account\n");
					return Prompts.menu;
					
				}
				
				user.getSaving().withdraw(getAmount());
				
			} catch (SavingAccountOverdraftException e) {

				System.err.println("\nInsufficient funds!\n");
			
			}
		
		return Prompts.menu;
		
	}
	
	public void passUser(User user) {
		
		this.user = user;
		
	}
	
	private double getAmount() {
		
		Scanner		scan	= new Scanner(System.in);
		double		amount	= 0;
		
		while(amount <= 0) {
			
			System.out.println("Enter an amount to withdraw");
			amount = SafeParser.parseDouble(scan.nextLine());
		
			if(amount < 0)
				
				System.err.println("\nError: Invalid entry\n");
			
		}
		
		return amount;
		
	}
	
	private AccountType getAccountType() {
		
		Scanner		scan		= new Scanner(System.in);
		AccountType	type;
		
		do {
		
			System.out.println("Which type of account would you like to withdraw to?");
			System.out.println("Checking or savings?");
			type = SafeParser.parseAccountType(scan.nextLine());
			
			if(type == null) System.err.println("\nError: Invalid selection\n");
			
		} while(type == null);

		return type;
		
	}

}
