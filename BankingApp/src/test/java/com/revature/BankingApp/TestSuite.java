package com.revature.BankingApp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	
	UserAndDoaTest.class,
	AccountAndTransTest.class,
	AccountCreationTest.class,
	PasswordTest.class
	
})

public class TestSuite {
	
	public TestSuite() {
		
	}

}
