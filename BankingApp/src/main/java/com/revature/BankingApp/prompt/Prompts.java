package com.revature.BankingApp.prompt;

import com.revature.BankingApp.prompt.prompts.*;

/**
 * Simply just holds singletons of the prompts
 * 
 * @author Kristoffer Spencer
 */
public final class Prompts {

	public final static CreateAdminPrompt 	mkAdmin 		= new CreateAdminPrompt();
	public final static CreateUserPrompt 	mkUser 			= new CreateUserPrompt();
	public final static LoginPrompt			login			= new LoginPrompt();
	public final static	LogoutPrompt		logout			= new LogoutPrompt();
	public final static	AdminMenuPrompt		adminMenu		= new AdminMenuPrompt();
	public final static MenuPrompt			menu			= new MenuPrompt();
	public final static ShutdownPrompt		shutdown		= new ShutdownPrompt();
	public final static OpenAccountPrompt	openAccount		= new OpenAccountPrompt();
	public final static CloseAccountPrompt	closeAccount	= new CloseAccountPrompt();
	public final static MakeDepositPrompt	deposit			= new MakeDepositPrompt();
	public final static MakeWithdrawPrompt	withdraw		= new MakeWithdrawPrompt();
	public final static PrintAllUsersPrompt allUsers		= new PrintAllUsersPrompt();
	public final static ShowAllAccountsPrompt	allAccounts		= new ShowAllAccountsPrompt();
	
}
