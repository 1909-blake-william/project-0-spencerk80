package com.revature.BankingApp.model;

import java.io.Serializable;

import com.revature.BankingApp.util.Password;

/**
 * Admin holds the basic
 * 
 * @author Kristoffer Spencer
 */
public class Admin implements Serializable {
	
	private static final long serialVersionUID = -7321461307035824567L;
	
	private String	username, passwd;
	
	private Admin() {}
	
	public Admin(String name, String pswd) {
		
		if(name.length() > 20) name = name.substring(0, 20);
		if(pswd.length() > 20) pswd = pswd.substring(0, 20);
		
		this.username = name;
		this.passwd = Password.transformPasswd(pswd, this.username);
		
	}
	
	public String getName() {
		
		return this.username;
		
	}
	
	public String getPassword() {
		
		return Password.transformPasswd(this.passwd, this.username);
		
	}
	
	@Override
	public String toString() {
	
		return this.username;
		
	}

}
