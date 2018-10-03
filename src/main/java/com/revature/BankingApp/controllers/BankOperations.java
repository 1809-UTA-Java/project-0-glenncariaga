package com.revature.BankingApp.controllers;

import java.util.ArrayList;

import com.revature.BankingApp.models.Account;
import com.revature.BankingApp.models.Transaction;
import com.revature.BankingApp.models.User;

public class BankOperations {
	
	public static Account openAccount() {
		Account newAccount = new Account();
		return newAccount;
	}
	
	public static ArrayList<Account> closeAccount(String accountId, ArrayList<Account> accounts) {
		int index = accounts.indexOf(accountId);		
		Account closingAccount = (Account) accounts.get(index);
		closingAccount.open = true;
		return accounts;
	}
	
	public static Account withdraw(Account acct, float withdrawal) {
		float newBalance = acct.balance - withdrawal;
		if(newBalance < 0) {
			System.out.println("Not Enough money in Account!");
			
		}else {
			acct.balance = newBalance;
		}
		
		return acct;
	}
	
	public static Account deposit(Account acct, float deposit) {
		acct.balance = acct.balance +deposit;
		return acct;
	}
	
	public static User addNewUser(String userName, String password) {
		User user = new User();
		user.role = "customer";
		user.userName = userName;
		user.password = password;
		return user;
	}
	
	public static void logout(ArrayList<Account> accounts, ArrayList<Transaction> transactions, ArrayList<User> users) {
		
		
		System.exit(0);
	}
	
	public static boolean authenticate(String[] usrPswd, ArrayList<User> accounts) {
		if(accounts.contains(usrPswd[0])) {
			int index = accounts.indexOf(usrPswd[0]);
			if(accounts.get(index).password.equals(usrPswd[1])) {
				return true;
			}
			
		}
		return false;
	}
}
