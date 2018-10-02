package com.revature.BankingApp.controllers;

import java.util.ArrayList;
import java.util.UUID;

import com.revature.BankingApp.models.Account;
import com.revature.BankingApp.models.Accounts;
import com.revature.BankingApp.models.User;

public class BankOperations {
	public static Account openAccount() {
		String accountId = UUID.randomUUID().toString();
		float balance = 0;
		Account newAccount = new Account(accountId,balance, true);
		return newAccount;
	}
	
	public static ArrayList<Account> closeAccount(String accountId, ArrayList<Account> accounts) {
		int index;
		for(int i = 0; i< accounts.size();i++) {
			if(accounts.get(i).getAccountId().equals(accountId)) {
				index = i;
			}
		}
		
		accounts.indexOf(accountId);
		Account closingAccount = (Account) accounts.get(index);
		closingAccount.setOpen(false);
		accounts.set(index, closingAccount);
		return accounts;
	}
	
	public static String withdraw(Account acct, float withdrawal) {
		float newBalance = acct.getBalance() - withdrawal;
		if(newBalance < 0) {
			return "error";
		}
		
		acct.setBalance(newBalance);
		return "success";
	}
	
	public static String deposit(Account acct, float deposit) {
		float newBalance = acct.getBalance() +deposit;
		acct.setBalance(newBalance);
		return "success";
	}
	
	public static String addNewUser(User user) {
		
		
		return "success";
	}
	
}
