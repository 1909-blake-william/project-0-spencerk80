package com.revature.BankingApp.prompt.prompts;

import java.util.Scanner;

import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;

/**
 * A prompt to logout
 * 
 * @author Kristoffer Spencer
 */
public class LogoutPrompt implements Prompt {
	
	@Override
	public Prompt run() {
		
		Scanner	scan		= new Scanner(System.in);
		String 	response;
		
		System.out.println("Are you sure you want to logout?");
		response = scan.nextLine();
		
		while(!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
			
			System.err.println("\n\nError: Invalid response\n\n");
			System.out.println("Are you sure you want to logout?");
			response = scan.nextLine();
			
		}
		
		if(response.equalsIgnoreCase("yes"))
			
			return Prompts.login;
		
		return Prompts.menu;
		
	}

}
