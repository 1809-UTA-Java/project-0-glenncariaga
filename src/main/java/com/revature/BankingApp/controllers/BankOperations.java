package com.revature.BankingApp.controllers;

import java.util.ArrayList;

import com.revature.BankingApp.models.Account;
import com.revature.BankingApp.models.Store;
import com.revature.BankingApp.models.Transaction;
import com.revature.BankingApp.models.User;
import com.revature.BankingApp.models.UserAccount;

public class BankOperations {
	
	public static Account openAccount() {
		Account newAccount = new Account();
		return newAccount;
	}
	
	public static UserAccount linkAccount(String accountId, String userId) {
		UserAccount ua = new UserAccount();
		ua.AccountId = accountId;
		ua.userId = userId;
		return ua;
	}
	
	public static ArrayList<Account> closeAccount(String accountId, ArrayList<Account> accounts) {
		int index = accounts.indexOf(accountId);		
		Account closingAccount = (Account) accounts.get(index);
		closingAccount.open = true;
		return accounts;
	}
	
	public static User addNewUser(String userName, String password) {
		User user = new User();
		user.role = "customer";
		user.userName = userName;
		user.password = password;
		
		return user;
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
	
	public static void logout(Store store) {
		System.out.println("Write was "+Services.writeToFile(store, "store"));
		System.exit(0);
	}
	
	public static boolean authenticate(String[] usrPswd, ArrayList<User> users) {
		
		for(User user:users) {
			if(user.userName.equals(usrPswd[0])&&user.password.equals(usrPswd[1])) {
				return true;
			}
		}
		return false;
	}

	public static User getUserInfo(String userName, ArrayList<User> users) {
		for(User user: users) {
			if(user.userName.equals(userName)) {
				return user;
			}
		}
		return null;
	}
}
