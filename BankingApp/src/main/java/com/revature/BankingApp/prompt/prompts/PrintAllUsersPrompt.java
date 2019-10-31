package com.revature.BankingApp.prompt.prompts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.revature.BankingApp.doa.UserDoa;
import com.revature.BankingApp.model.User;
import com.revature.BankingApp.prompt.Prompt;
import com.revature.BankingApp.prompt.Prompts;

/**
 * A prompt that allows the admin to see all the registered user in the system
 * 
 * @author Kristoffer Spencer
 */
public class PrintAllUsersPrompt implements Prompt {

	@Override
	public Prompt run() {
		
		List<User>	users;
		
		try {
			
			users = UserDoa.doa.getAllUsers();
			
		} catch(IOException e) {
			
			System.err.println("\n" + e.getMessage() + "\n");
			return Prompts.adminMenu;
			
		}
		
		if(users.size() == 0) {
			
			System.out.println("No users exist");
			return Prompts.adminMenu;
			
		}
		
		for(User user : users)
			
			System.out.println(user.toString());
		
		return Prompts.adminMenu;
		
		
	}

}
