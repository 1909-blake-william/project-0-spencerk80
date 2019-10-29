package com.revature.BankingApp;

import com.revature.BankingApp.doa.AdminDoa;
import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;
import com.revature.BankingApp.prompt.prompts.ShutdownPrompt;

public class BankDriver {

	public static void main(String[] args) {

		Prompt	p;
		
		if(!AdminDoa.doa.anyAdminExists()) {
			
			System.out.println("Welcome to Revature bank.");
			System.out.println("Running first time setup");
			p = Prompts.mkAdmin;
			
		}
		
		else
			
			p = Prompts.login;
		
		while(!(p instanceof ShutdownPrompt))
			
			p = p.run();
			
		
		System.out.println("\n\n--Shutting down--\n\n");

	}

}
