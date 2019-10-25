package com.revature.BankingApp.util;

import com.revature.BankingApp.enums.AccountType;
import com.revature.BankingApp.enums.TransactionType;

/**
 * A utility class to quickly get a safe value from strings
 * 
 * @author Kristoffer Spencer
 */
public final class SafeParser {

	public static AccountType parseAccountType(String s) {
		
		try {
			
			return AccountType.valueOf(s.toUpperCase());
			
		} catch(Exception e) {
			
			return null;
			
		}
		
	}
	
	public static TransactionType parseTransType(String s) {
		
		try {
			
			return TransactionType.valueOf(s.toUpperCase());
			
		} catch(Exception e) {
			
			return null;
			
		}
		
	}
	
	public static int parseInt(String s) {
		
		try {
			
			return Integer.parseInt(s);
			
		} catch(Exception e) {
			
			return -1;
			
		}
		
	}
	
	public static double parseDouble(String s) {
		
		try {
			
			return Double.parseDouble(s);
			
		} catch(Exception e) {
			
			return -1f;
			
		}
		
	}
	
}
