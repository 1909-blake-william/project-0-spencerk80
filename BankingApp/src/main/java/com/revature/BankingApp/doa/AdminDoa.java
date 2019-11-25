package com.revature.BankingApp.doa;

import java.io.IOException;
import java.util.List;

import com.revature.BankingApp.model.Admin;
import com.revature.BankingApp.model.User;

/**
 * A public interface used for working with storing the admin data
 * @author Kristoffer Spencer
 */
public interface AdminDoa {
	
	AdminDoa	doa	= new AdminDoaSql();
	
	void 	saveAdminInfo(Admin admin) throws IOException;
	Admin 	getAdminInfo(String name) throws IOException;
	boolean	adminExists(String name);
	boolean	anyAdminExists();

}
