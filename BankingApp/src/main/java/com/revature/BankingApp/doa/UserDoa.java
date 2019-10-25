package com.revature.BankingApp.doa;

import java.io.IOException;
import java.util.List;

import com.revature.BankingApp.enums.AccountType;
import com.revature.BankingApp.model.Account;
import com.revature.BankingApp.model.Transaction;
import com.revature.BankingApp.model.User;

/**
 * A public interface used for working with storing the user data
 * @author Kristoffer Spencer
 */
public interface UserDoa {
	
	UserDoa	doa	= new UserDoaSql();
	
	void 	saveUserInfo(User user) throws IOException;
	User 	getUserInfo(String username) throws IOException;
	boolean	userExists(String name);
	List<User> getAllUsers() throws IOException;
	void	closeAccount(User user, AccountType account) throws IOException;

}
