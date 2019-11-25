package com.revature.BankingApp.doa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.BankingApp.enums.AccountType;
import com.revature.BankingApp.model.Account;
import com.revature.BankingApp.model.Transaction;
import com.revature.BankingApp.model.User;
import com.revature.BankingApp.util.ConnectionUtil;
import com.revature.BankingApp.util.Password;
import com.revature.BankingApp.util.SafeParser;

public class UserDoaSql implements UserDoa {

	@Override
	public User getUserInfo(String username) throws IOException {

		String				name,
							pswd;
		double				svngBal			= -1,
							chckBal			= -1;
		AccountType			accT;
		PreparedStatement	ps;
		ResultSet			rs;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			//Get the user data
			ps = c.prepareStatement("SELECT users.name, users.passwd FROM users WHERE users.name = '" + username + "'");
			rs = ps.executeQuery();
			rs.next();
			name = rs.getString("name");
			pswd = Password.transformPasswd(rs.getString("passwd"), name);
			
			//Get account info
			ps = c.prepareStatement("SELECT accounts.bal, accounts.acc_t FROM accounts INNER JOIN "
								  + "users ON accounts.owner = users.id WHERE users.name = '" + username + "'");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				accT = SafeParser.parseAccountType(rs.getString("acc_t"));
				
				if(accT == AccountType.CHECKING)
					
					chckBal = rs.getDouble("bal");
				
				else if(accT == AccountType.SAVINGS)
					
					svngBal = rs.getDouble("bal");
				
			}
			
			//Piece it all together
			if(svngBal > 0 && chckBal > 0)
				
				return new User(name, pswd, new Account(AccountType.CHECKING, chckBal, null),
											new Account(AccountType.SAVINGS, svngBal, null));
			
			else if(chckBal < 0 && svngBal < 0)
				
				return new User(name, pswd);
			
			else if(chckBal > 0)
				
				return new User(name, pswd, new Account(AccountType.CHECKING, chckBal, null));
			
			else
				
				return new User(name, pswd, new Account(AccountType.SAVINGS, svngBal, null));
			
			
		} catch(SQLException e) {
			
			throw new IOException(e.getMessage());
			
		}

	}

	@Override
	public boolean userExists(String name) {

		try {
			
			getUserInfo(name);
			return true;
			
		} catch(Exception e) {
			
			return false;
			
		}
		
	}

	@Override
	public List<User> getAllUsers() throws IOException {

		PreparedStatement	ps;
		ResultSet			rs;
		List<String>		names		= new ArrayList<>();
		List<User>			users		= new ArrayList<>();
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement("SELECT users.name FROM users WHERE name != 'closed'");
			rs = ps.executeQuery();
			
			while(rs.next())
				
				names.add(rs.getString("name"));
			
		} catch(SQLException e) {
			
			throw new IOException(e.getMessage());
			
		}
		
		for(String name : names)
			
			users.add(getUserInfo(name));
		
		return users;
		
	}
	
	@Override
	public void saveUserInfo(User user) throws IOException {

		PreparedStatement 	ps;
		ResultSet			rs;
		String				username 	= user.getUserName(),
							passwd		= Password.transformPasswd(user.getPassword(), user.getUserName());
			
		try(Connection c = ConnectionUtil.getConnection()) {
			
			saveAccountInfo(user);
			
			ps = c.prepareStatement("SELECT users.name FROM users WHERE users.name = '" + user.getUserName() + "'");
			rs = ps.executeQuery();
			
			if(rs.next()) return;
			
			ps = c.prepareStatement("INSERT INTO users (id, name, passwd) VALUES (user_id_seq.NEXTVAL, '" + username + "', '" + passwd + "')");
			ps.executeUpdate();
			
		} catch(SQLException e) {
			
			throw new IOException(e.getMessage());
			
		}
		
	}

	private void saveAccountInfo(User user) throws IOException {

		PreparedStatement 	ps;
		ResultSet			rs;
		Account				account = user.getChecking();
		int					id;
		
		for(int i = 0; i < 2; i++) {
		
			if(account == null) continue;
			
			try(Connection c = ConnectionUtil.getConnection()) {
				
				ps = c.prepareStatement("SELECT accounts.id "
						+ "FROM accounts INNER JOIN users ON users.ID "
						+ "= accounts.owner WHERE users.name = '" + user.getUserName() + "' " 
						+ "AND accounts.acc_t = '" + account.getAccType().toString() + "'");
				rs = ps.executeQuery();
				
				if(rs.next()) {
					
					ps = c.prepareStatement("UPDATE accounts " + 
							"SET accounts.bal = " + account.getBalance() + 
							"WHERE accounts.owner = 1 " +
							"AND accounts.ACC_T = '" + account.getAccType().toString() + "'");
					ps.executeUpdate();
					
				}
				
				else {
					
					ps = c.prepareStatement("SELECT id FROM users WHERE name = '" + user.getUserName() + "'");
					rs = ps.executeQuery();
					rs.next();
					id = rs.getInt("id");
					
					ps = c.prepareStatement("INSERT INTO accounts (id, owner, bal, acc_t) VALUES (account_id_seq.NEXTVAL ,?,?,?)");
					ps.setInt(1, id);
					ps.setDouble(2, account.getBalance());
					ps.setString(3, account.getAccType().toString());
					ps.executeUpdate();
					
				}
				
			} catch(SQLException e) {
				
				throw new IOException(e.getMessage());
				
			}
			
			account = user.getSaving();
		
		}
		
		try {
			
			TransactionDoa.doa.saveTransactions(user);
			
		} catch(SQLException e) {
			
			throw new IOException(e.getMessage());
			
		}
		
	}

	@Override
	public void closeAccount(User user, AccountType account) throws IOException {

		PreparedStatement	ps;
		ResultSet			rs;
		int					owner;
		
		try(Connection c = ConnectionUtil.getConnection()) {

			ps = c.prepareStatement("SELECT owner FROM accounts " +
									"INNER JOIN users ON users.id = accounts.owner " +
									"WHERE users.name = ? AND accounts.acc_t = ?");
			ps.setString(1, user.getUserName());
			ps.setString(2, account.toString());
			rs = ps.executeQuery();
			rs.next();
			owner = rs.getInt("owner");
			
			ps = c.prepareStatement("UPDATE accounts SET bal = 0 " +
									"WHERE owner = " + owner + " AND acc_t = '" + account.toString() + "'");
			
			ps = c.prepareStatement("UPDATE accounts SET owner = -1, prev_owner = " + owner +
									" WHERE owner = " + owner  + " AND acc_t = '" + account.toString() + "'");
			ps.executeUpdate();
			
		} catch(SQLException e) {
			
			throw new IOException(e.getMessage());
			
		}
		
	}

}
