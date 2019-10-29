package com.revature.BankingApp.prompt.prompts;

import java.util.Scanner;

import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;

/**
 * Verifies that the admin wants to shutdown the bank app
 * 
 * @author Kristoffer Spencer
 */
public class ShutdownPrompt implements Prompt {

	/**
	 * Run the prompt and ask if the admin wants to shutdown
	 */
	public Prompt run() {
		
		if(adminWantsShutoff())
			
			return Prompts.shutdown;
		
		else
			
			return Prompts.adminMenu;
		
	}
	
	private boolean adminWantsShutoff() {
		
		Scanner	scan		= new Scanner(System.in);
		String	response;
		
		System.out.println("Do you want to shutdown the system?");
		response = scan.nextLine();
		
		while(!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
			
			System.err.println("\nError: Response not recognized\n");
			System.out.println("Do you want to shutdown the system?");
			response = scan.nextLine();
			
		}
		
		if(response.equalsIgnoreCase("yes"))
			
			return true;
		
		else
			
			return false;
		
	}

}
