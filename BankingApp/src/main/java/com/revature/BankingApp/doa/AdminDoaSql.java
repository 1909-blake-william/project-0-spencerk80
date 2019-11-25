package com.revature.BankingApp.doa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.BankingApp.model.Admin;
import com.revature.BankingApp.util.ConnectionUtil;
import com.revature.BankingApp.util.Password;

public class AdminDoaSql implements AdminDoa {

	@Override
	public void saveAdminInfo(Admin admin) throws IOException {

		Connection			c;
		PreparedStatement 	ps;
		String				username 	= admin.getName(),
							passwd		= Password.transformPasswd(admin.getPassword(), admin.getName());
		
		try {
			
			c = ConnectionUtil.getConnection();
			
		} catch(SQLException e) {
			
			throw new IOException("Could not connect to the db");
			
		} try {
			ps = c.prepareStatement("INSERT INTO admins (id, name, passwd) VALUES (admin_id_seq.NEXTVAL, '" + username + "', '" + passwd + "')");
			ps.execute();
			
		} catch(SQLException e) {
			
			throw new IOException(e.getMessage());
			
		}

	}

	@Override
	public Admin getAdminInfo(String name) throws IOException {

		Connection			c;
		PreparedStatement	ps;
		ResultSet			rs;
		Admin				admin		= null;
		String				adminName,
							pswd;
		
		try {
			
			c = ConnectionUtil.getConnection();
			ps = c.prepareStatement("SELECT name, passwd FROM admins WHERE name = '" + name + "'");
			rs = ps.executeQuery();
			
		} catch(SQLException e) {
			
			throw new IOException(e.getMessage());
			
		} try {
			
			rs.next();
			adminName 	= rs.getString("name");
			pswd		= Password.transformPasswd(rs.getString("passwd"), adminName);
			admin 		= new Admin(adminName, pswd);
			
		} catch(SQLException e) {
			
			//Leave admin as null
			
		}
		
		return admin;
		
	}

	@Override
	public boolean adminExists(String name) {
		
		try {

			return getAdminInfo(name) != null;
			
		} catch(IOException e) {
			
			System.err.println("\n\nError reading database\n\n");
			return false;
			
		}
		
	}

	@Override
	public boolean anyAdminExists() {

		Connection			c;
		PreparedStatement	ps;
		ResultSet			rs;
		
		try {
			
			c = ConnectionUtil.getConnection();
			ps = c.prepareStatement("SELECT * FROM admins");
			rs = ps.executeQuery();
			rs.next();
			
			if(rs.getString("name").equals("")) return false;
			
		} catch(SQLException e) {
			
			return false;
			
		}
		
		return true;
		
	}

}
