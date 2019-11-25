package com.revature.BankingApp.prompt.prompts;

import java.util.Scanner;

import com.revature.BankingApp.enums.AccountType;
import com.revature.BankingApp.model.User;
import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;
import com.revature.BankingApp.util.SafeParser;

/**
 * A prompt to allow the user to make a deposit
 * 
 * @author Kristoffer Spencer
 */
public class MakeDepositPrompt implements Prompt {

	User user;
	
	@Override
	public Prompt run() {

		AccountType type = getAccountType();
		
		if(type == AccountType.CHECKING)
			
			if(user.getChecking() == null) {
				
				System.err.println("\nError: No such account\n");
				return Prompts.menu;
				
			} else
			
				user.getChecking().deposit(getAmount());
		
		else
			
			if(user.getSaving() == null) {
				
				System.err.println("\nError: No such account\n");
				return Prompts.menu;
				
			} else
			
				user.getSaving().deposit(getAmount());
		
		return Prompts.menu;
		
	}
	
	public void passUser(User user) {
		
		this.user = user;
		
	}
	
	private double getAmount() {
		
		Scanner		scan	= new Scanner(System.in);
		double		amount	= 0;
		
		while(amount <= 0) {
			
			System.out.println("Enter an amount to deposit");
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
		
			System.out.println("Which type of account would you like to deposit to?");
			System.out.println("Checking or savings?");
			type = SafeParser.parseAccountType(scan.nextLine());
			
			if(type == null) System.err.println("\nError: Invalid selection\n");
			
		} while(type == null);

		return type;
		
	}

}
