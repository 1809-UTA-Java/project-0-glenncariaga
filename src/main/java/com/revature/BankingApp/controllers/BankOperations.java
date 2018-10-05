package com.revature.BankingApp.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

	public static Account withdraw(String acct, float withdrawal, ArrayList<Account> accounts, String userId,
			ArrayList<UserAccount> userAccounts) {
		boolean found = false;
		boolean owner = false;
		boolean closed = false;
		Account activeAccount = new Account();
		for (Account account : accounts) {
			if (acct.equals(account.accountId)) {
				activeAccount = account;
				found = true;
				if (!account.open) {
					closed = true;
				}
			}
		}

		if (closed) {
			System.out.println(activeAccount.accountId + " is closed!");
			return null;
		}
		if (!found) {
			System.out.println("Account Not Found!");
			return null;
		}

		for (UserAccount ua : userAccounts) {
			if (ua.AccountId.equals(activeAccount.accountId) && ua.userId.equals(userId)) {
				owner = true;
			}
		}

		if (!owner) {
			System.out.println("You don't own this account!");
			return null;
		}

		float newBalance = activeAccount.balance - withdrawal;

		if (withdrawal <= 0) {
			System.out.println("Please enter an amount greater than 0.01!");
			return null;
		}

		if (newBalance < 0) {
			System.out.println("Not Enough money in Account!");

		} else {
			activeAccount.balance = newBalance;
		}

		return activeAccount;
	}

	public static Account deposit(String acct, float deposit, ArrayList<Account> accounts) {
		boolean found = false;
		boolean closed = false;
		Account activeAccount = new Account();
		for (Account account : accounts) {
			if (acct.equals(account.accountId)) {
				activeAccount = account;
				found = true;
				if (!account.open) {
					closed = true;
				}
			}
		}

		if (closed) {
			System.out.println(activeAccount.accountId + " is closed!");
			return null;
		}
		if (deposit <= 0) {
			System.out.println("Please enter an amount greater than 0.01!");
			return null;
		}
		if (!found) {
			System.out.println("Account Not Found!");
			return null;
		}
		activeAccount.balance = activeAccount.balance + deposit;
		return activeAccount;
	}

	public static void logout(Store store) {
		System.out.println("Write was " + Services.writeToFile(store, "store"));
		System.exit(0);
	}

	public static boolean authenticate(String[] usrPswd, ArrayList<User> users) {

		for (User user : users) {
			if (user.userName.equals(usrPswd[0]) && user.password.equals(usrPswd[1])) {
				return true;
			}
		}
		return false;
	}

	public static User getUserInfo(String userName, ArrayList<User> users) {
		for (User user : users) {
			if (user.userName.equals(userName)) {
				return user;
			}
		}
		return null;
	}

	public static boolean checkUserNameDup(ArrayList<User> users, String entry) {
		boolean dup = false;
		for (User user : users) {
			if (user.userName.equals(entry)) {
				dup = true;
			}
		}
		return dup;
	}

	public static boolean checkAccountId(ArrayList<Account> accounts, String accountId) {
		boolean notValid = true;
		for(Account account: accounts) {
			if(account.accountId.equals(accountId)) {
				notValid = false;
			}
		}
		
		return notValid;
	}

	public static Transaction requestApproval(HashMap<String, String> config) {
		// this is the format of the hashmap
//		config.put("userId",);
//		config.put("accountId",);
//		config.put("reviewed",));
//		config.put("approval",);
//		config.put("action",);
//		config.put("amount",);
		
		//this is the corresponding transaction model
//		public String userId;
//		public String toUserId;
//		public String accountId;
//		public String toAccountId;
//		public String action;
//		public float amount;
//		public String reviewer;
		
		//converts the config to a transaction object
		Transaction transact = new Transaction();
		transact.action = config.get("action");
		transact.userId = config.get("userId");
		if(config.containsKey("accountId")) {
			transact.accountId = config.get("accountId");
		}
		if(config.containsKey("userId")) {
			transact.accountId = config.get("userId");
		}
		if(config.containsKey("toUserId")) {
			transact.accountId = config.get("toUserId");
		}
		if(config.containsKey("toAccountId")) {
			transact.accountId = config.get("toAccountId");
		}
		if(config.containsKey("amount")) {
			transact.amount = Float.parseFloat(config.get("amount"));
		}
		
		return transact;
		
	}

	public static Transaction quickApproval(Transaction transact, Store store) {
		int countAccounts = 0;
		for(UserAccount action: store.userAccounts) {
			if(action.userId.equals(transact.userId)) {
				countAccounts++;
			}
		}
		
		switch(transact.action){
			case "deposit":
				if(transact.amount<10000) {
					transact.approval = true;
					transact.reviewed = true;
					transact.reviewer = "auto";
					return transact;
				}
				break;
			case "withdrawal":
				if(transact.amount<10000) {
					transact.approval = true;
					transact.reviewed = true;
					transact.reviewer = "auto";
					return transact;
				}
				break;
			case "transferFunds":
				if(transact.amount<10000) {
					transact.approval =true;
					transact.reviewed = true;
					transact.reviewer = "auto";
					return transact;
				}
				break;
			case "openAccount":
				
				if(countAccounts <= 0) {
					transact.approval = true;
					transact.reviewed = true;
					transact.reviewer = "auto";
					return transact;
				}
				break;
			case "closeAccount":
				if(countAccounts > 1) {
					transact.approval = true;
					transact.reviewed = true;
					transact.reviewer = "auto";
					return transact;
				}
				break;
			case "addUserToAccount":
				break;
		}
		
		return null;
	}
}
