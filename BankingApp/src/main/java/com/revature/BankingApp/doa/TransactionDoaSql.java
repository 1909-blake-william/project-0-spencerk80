package com.revature.BankingApp.doa;

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
import com.revature.BankingApp.util.SafeParser;

public class TransactionDoaSql implements TransactionDoa {

	@Override
	public List<Transaction> getTransactions(User user, AccountType account) throws SQLException {

		List<Transaction> 	list	= new ArrayList<>();
		PreparedStatement	ps;
		ResultSet			rs;
		int					accID;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement("SELECT accounts.id FROM accounts INNER JOIN users " +
									"ON users.id = accounts.owner " +
									"WHERE users.name = ? AND accounts.acc_t = ?");
			ps.setString(1, user.getUserName());
			ps.setString(2, account.toString());
			rs = ps.executeQuery();
			rs.next();
			accID = rs.getInt("id");
			
			ps = c.prepareStatement("SELECT * FROM transactions INNER JOIN accounts " +
									"ON transactions.acc = accounts.id INNER JOIN users " +
									"ON accounts.owner = users.id " +
									"WHERE users.name = ? AND accounts.id = ? " +
									"ORDER BY t_stamp DESC");
			ps.setString(1, user.getUserName());
			ps.setInt(2, accID);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				list.add(new Transaction(SafeParser.parseTransType(rs.getString("trn_t")), 
										 rs.getDouble("bal"), rs.getString("t_stamp")));
				
			}
			
		}
		
		return list;
		
	}

	@Override
	public void saveTransactions(User user) throws SQLException {

		PreparedStatement	ps;
		ResultSet			rs;
		Account				account = user.getChecking();
		int					accID;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			for(int i = 0; i < 2; i++) {
				
				if(account == null) continue;
				
				for(Transaction t : account.getTransactions()) {
					
					ps = c.prepareStatement("SELECT accounts.id FROM accounts INNER JOIN users " +
											"ON users.id = accounts.owner " +
											"WHERE users.name = ? AND accounts.acc_t = ?");
					ps.setString(1, user.getUserName());
					ps.setString(2, account.getAccType().toString());
					rs = ps.executeQuery();
					rs.next();
					accID = rs.getInt("id");
					
					ps = c.prepareStatement("INSERT INTO transactions (id, acc, bal, trn_t, t_stamp) " +
											"VALUES (transaction_id_seq.NEXTVAL, ?, ?, ?, ?)");
					ps.setInt(1, accID);
					ps.setDouble(2, t.getAmount());
					ps.setString(3, t.getTransType().toString());
					ps.setString(4, t.getTimestamp());
					ps.executeUpdate();
					
				}
				
				account.getTransactions().clear();
				account = user.getSaving();
				
			}
		
		}

	}

}
