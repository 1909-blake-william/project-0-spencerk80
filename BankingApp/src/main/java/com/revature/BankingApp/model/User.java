package com.revature.BankingApp.model;

import java.io.IOException;
import java.io.Serializable;

import com.revature.BankingApp.doa.UserDoa;
import com.revature.BankingApp.enums.AccountType;
import com.revature.BankingApp.exceptions.AccountAlreadyExistsException;
import com.revature.BankingApp.exceptions.AccountHasBalanceException;
import com.revature.BankingApp.exceptions.OpenWithNegBalException;
import com.revature.BankingApp.util.Password;

/**
 * A bare-bones class to store user name and password
 * @author Kristoffer Spencer
 */
public class User implements Serializable {

	private static final long serialVersionUID = -5837586714914066505L;
	private	String			name,
							passwd;
	private	final Account[]	accounts;
	
	/**
	 * Constructor provided as the only means to store the data.
	 * Class doesn't provide setters because data shouldn't be changed.
	 * If password would need to be updated, just create a new obj for that
	 * user to update their password.
	 * @param username: The name of the user
	 * @param password: The password for the user
	 */
	private User() {
	
		//This isn't to be used. Just wouldn't let me compile without
		//this bit here. Specifically marked the empty constructor as
		//private because it should never be used.
		this("Error", "Nope");
		
	}
	
	public User(String username, String password) {
		
		if(username.length() > 20) username = username.substring(0, 20);
		if(password.length() > 20) password = password.substring(0, 20);
		
		this.name 		= username.trim();
		this.passwd		= Password.transformPasswd(password.trim(), this.name);
		this.accounts	= new Account[2];
		
	}
	
	public User(String username, String passwd, Account acc) {
		
		this(username, passwd);
		
		if(acc.getAccType() == AccountType.CHECKING)
			
			this.accounts[AccountType.CHECKING.ordinal()] = acc;
		
		else
			
			this.accounts[AccountType.SAVINGS.ordinal()] = acc;
		
	}
	
	public User(String username, String passwd, Account chck, Account svng) {
		
		this(username, passwd);
		
		this.accounts[AccountType.CHECKING.ordinal()] = chck;
		this.accounts[AccountType.SAVINGS.ordinal()] = svng;
		
	}
	
	//Sets up a new saving account with any amount of money the user wants to start with
	public void openSaving(double amount) throws OpenWithNegBalException, AccountAlreadyExistsException {
		
		//If the amount is negative, throw an exception
		if(amount < 0) throw new OpenWithNegBalException();
		
		if(this.accounts[AccountType.SAVINGS.ordinal()] == null)
		
			this.accounts[AccountType.SAVINGS.ordinal()] = new Account(AccountType.SAVINGS, amount);
		
		else
			
			throw new AccountAlreadyExistsException();
		
	}
	
	public void closeSaving() throws AccountHasBalanceException {
		
		if(this.getSaving().getBalance() > 0.0)
			
			throw new AccountHasBalanceException();
		
		else {
			
			try {
				
				UserDoa.doa.closeAccount(this, AccountType.SAVINGS);
			
			} catch (IOException e) {
			
				System.err.println(e.getMessage());
			
			}
			
			this.accounts[AccountType.SAVINGS.ordinal()] = null;
			
		}
		
	}
	
	public void openChecking(double amount) throws OpenWithNegBalException, AccountAlreadyExistsException {
		
		//If the amount is negative, throw an exception
		if(amount < 0) throw new OpenWithNegBalException();
		
		if(this.accounts[AccountType.CHECKING.ordinal()] == null)
			
			this.accounts[AccountType.CHECKING.ordinal()] = new Account(AccountType.CHECKING, amount);
		
		else
			
			throw new AccountAlreadyExistsException();
		
	}
	
	public void closeChecking() throws AccountHasBalanceException {
		
		if(this.getChecking().getBalance() != 0.0)
			
			throw new AccountHasBalanceException();
		
		else {
			
			try {
				
				UserDoa.doa.closeAccount(this, AccountType.CHECKING);
			
			} catch (IOException e) {
			
				System.err.println(e.getMessage());
			
			}
			
			this.accounts[AccountType.CHECKING.ordinal()] = null;
			
		}
		
	}
	
	public Account getSaving() {
		
		return this.accounts[AccountType.SAVINGS.ordinal()];
		
	}
	
	public Account getChecking() {
		
		return this.accounts[AccountType.CHECKING.ordinal()];
		
	}
	
	public String getUserName() {
		
		return this.name;
		
	}
	
	public String getPassword() {
		
		return Password.transformPasswd(this.passwd, this.name);
		
	}
	
	/**
	 * Over ride the toString to just return the name only.
	 * Easy way to see who this object is for
	 */
	@Override
	public String toString() {
		
		return this.name;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
