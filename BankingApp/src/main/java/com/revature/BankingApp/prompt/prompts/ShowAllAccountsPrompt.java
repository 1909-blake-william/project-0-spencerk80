package com.revature.BankingApp.prompt.prompts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.revature.BankingApp.doa.AdminDoa;
import com.revature.BankingApp.doa.UserDoa;
import com.revature.BankingApp.model.Transaction;
import com.revature.BankingApp.model.User;
import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;

public class ShowAllAccountsPrompt implements Prompt {

	@Override
	public Prompt run() {
		
		List<User> users = null;
		
		try{
			
			users = UserDoa.doa.getAllUsers();
			
		} catch(IOException e) {
			
			System.err.println("\nError: Could not read files\n");
			return Prompts.adminMenu;
			
		}
		
		if(users.size() == 0) {
			
			System.out.println("No users exist");
			return Prompts.adminMenu;
			
		}
		
		for(User user : users) {
			
			System.out.println("\n--" + user.toString() + "--");
			
			if(user.getChecking() != null) 
				
				System.out.printf("Checking account: $%.2f\n", user.getChecking().getBalance());
			
			if(user.getSaving() != null) 
				
				System.out.printf("Savings account: $%.2f\n", user.getSaving().getBalance());
					
		}
		
		return Prompts.adminMenu;
		
	}

}
