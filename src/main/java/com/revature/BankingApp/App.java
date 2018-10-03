package com.revature.BankingApp;

import java.util.ArrayList;

import com.revature.BankingApp.controllers.BankOperations;
import com.revature.BankingApp.controllers.Services;
import com.revature.BankingApp.models.Account;
import com.revature.BankingApp.models.Transaction;
import com.revature.BankingApp.models.User;
import com.revature.BankingApp.view.Screen;

/**
 *
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	//Initialize State load files, if it exists, if not, new instance
    	// of each arraylist
    	ArrayList<User> users = Services.readToObj("users");
    	ArrayList<Account> accounts = Services.readToObj("accounts");
    	ArrayList<Transaction> transactions = Services.readToObj("transactions");
    	
    	//a welcome screen
    	Screen.splash();
    	//the login screen.
    	switch(Screen.loginScreen()) {
    		case "0":
    			BankOperations.logout(accounts, transactions, users);
    			break;
    		case "1":
    			User user = Screen.registration();
    			users.add(user);
    			break;
    			
    		default: 
    			
    	}
    	
    	//this is the screen to login a user
    	String loggedInUser;
		int failedTries = 0;
		boolean authenticate = false;
		while(!authenticate){
			String[] usrPswd = Screen.authentication();
			authenticate = BankOperations.authenticate(usrPswd, users);
			if(!authenticate) {
				System.out.println("Authentication Failed.");
			}
			failedTries = failedTries+1;
			if(failedTries >3) {
				BankOperations.logout(accounts, transactions, users);
				break;
			}
			if(authenticate) {
//				1loggedInUser
			}
		}
		
		//the user screen, after authentication
		switch(Screen.userScreen()) {
		case "0": 
			BankOperations.logout(accounts, transactions, users);
			break;
		case "1":
			if(Screen.addAccountScreen().contentEquals("y")) {
				BankOperations.openAccount();
			}
			break;
		case "2":
			break;
		}
    	
    }
}
