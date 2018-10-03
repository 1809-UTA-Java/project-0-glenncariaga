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
    	ArrayList<User> users =Services.readToObj("users");
    	ArrayList<Account> accounts = Services.readToObj("accounts");
    	ArrayList<Transaction> transactions = Services.readToObj("transactions");
    	
    	Screen.splash();
    	switch(Screen.loginScreen()) {
    		case "0":
    			BankOperations.logout(accounts, transactions, users);
    			break;
    		case "1":
    			Screen.registration();
    			break;
    		default: 
    			String[] usrPswd = Screen.authentication();
    			int failedTries = 0;
    			while(!BankOperations.authenticate(usrPswd, users)){
    				System.out.println("Authentication Failed.");
    				failedTries = failedTries++;
    				if(failedTries >3) {
    					BankOperations.logout(accounts, transactions, users);
    				}
    			}
    			
    	}
    	
    }
}
