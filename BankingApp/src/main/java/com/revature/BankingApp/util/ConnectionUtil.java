package com.revature.BankingApp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	public static Connection getConnection() throws SQLException {
		
		String		url			= System.getenv("BANK_DB_URL"),
					username	= System.getenv("BANK_DB_USERNAME"),
					password	= System.getenv("BANK_DB_PASSWD");
		
		return DriverManager.getConnection(url, username, password);
		
	}

}
