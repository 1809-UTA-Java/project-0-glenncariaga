package com.revature.BankingApp.controllers;


import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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

	public static CopyOnWriteArrayList<Account> closeAccount(String accountId, CopyOnWriteArrayList<Account> accounts) {
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

	public static Account withdraw(String acct, float withdrawal, CopyOnWriteArrayList<Account> accounts, String userId,
			CopyOnWriteArrayList<UserAccount> userAccounts) {
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

	public static Account deposit(String acct, float deposit, CopyOnWriteArrayList<Account> accounts) {
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

	public static boolean authenticate(String[] usrPswd, CopyOnWriteArrayList<User> users) {

		for (User user : users) {
			if (user.userName.equals(usrPswd[0]) && user.password.equals(usrPswd[1])) {
				return true;
			}
		}
		return false;
	}

	public static User getUserInfo(String userName, CopyOnWriteArrayList<User> users) {
		for (User user : users) {
			if (user.userName.equals(userName)) {
				return user;
			}
		}
		return null;
	}

	public static boolean checkUserNameDup(CopyOnWriteArrayList<User> users, String entry) {
		boolean dup = false;
		for (User user : users) {
			if (user.userName.equals(entry)) {
				dup = true;
			}
		}
		return dup;
	}

	public static boolean checkAccountId(CopyOnWriteArrayList<Account> accounts, String accountId) {
		boolean notValid = true;
		for (Account account : accounts) {
			if (account.accountId.equals(accountId)) {
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

		// this is the corresponding transaction model
//		public String userId;
//		public String toUserId;
//		public String accountId;
//		public String toAccountId;
//		public String action;
//		public float amount;
//		public String reviewer;

		// converts the config to a transaction object
		Transaction transact = new Transaction();
		transact.action = config.get("action");
		transact.userId = config.get("userId");
		if (config.containsKey("accountId")) {
			transact.accountId = config.get("accountId");
		}
		if (config.containsKey("userId")) {
			transact.userId = config.get("userId");
		}
		if (config.containsKey("toUserId")) {
			transact.toUserId = config.get("toUserId");
		}
		if (config.containsKey("toAccountId")) {
			transact.toAccountId = config.get("toAccountId");
		}
		if (config.containsKey("amount")) {
			transact.amount = Float.parseFloat(config.get("amount"));
		}
		
		return transact;

	}

	public static Transaction quickApproval(Transaction transact, Store store) {
		int countAccounts = 0;
		for (UserAccount action : store.userAccounts) {
			if (action.userId.equals(transact.userId)) {
				countAccounts++;
			}
		}

		switch (transact.action) {
		case "deposit":
			if (transact.amount < 10000) {
				transact.approval = true;
				transact.reviewed = true;
				transact.reviewer = "auto";
				return transact;
			}
			break;
		case "withdrawal":
			if (transact.amount < 10000) {
				transact.approval = true;
				transact.reviewed = true;
				transact.reviewer = "auto";
				return transact;
			}
			break;
		case "transferFunds":
			if (transact.amount < 10000) {
				transact.approval = true;
				transact.reviewed = true;
				transact.reviewer = "auto";
				return transact;
			}
			break;
		case "openAccount":

			if (countAccounts <= 0) {
				transact.approval = true;
				transact.reviewed = true;
				transact.reviewer = "auto";
				return transact;
			}
			break;
		case "closeAccount":
			if (countAccounts > 1) {
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

	public static Store processApproval(Transaction transact, Store store, User activeUser) {
		switch (transact.action) {
		case "addAccount":
			store = addAccountAction(store, transact);
			break;
		case "transferFunds":
			store = transferFundsAction(store, transact);
			break;
		case "withdraw":
			store = withdrawalActions(store, transact);
			break;
		case "deposit":
			store = depositActions(store,transact);
			break;
		case "jointAccount":
			store = jointAccountAction(store,transact);
		}
		transact.approval =true;
		transact.reviewer =activeUser.userId;
		for(Transaction element : store.transactions) {
			if(element.transactionID.equals(transact.transactionID)) {
				element = transact;
			}
		}
		
		return store;
	}
	
	public static Store jointAccountAction(Store store, Transaction transact) {
		UserAccount userAccount = BankOperations.jointAccount(store, transact);
		if(userAccount!=null) {
			store.userAccounts.add(userAccount);
		}
		return store;
	}

	private static UserAccount jointAccount(Store store, Transaction transact) {
		UserAccount ua = new UserAccount();
		for(UserAccount userAccount: store.userAccounts) {
		//check ownership of the account
			if(transact.userId.equals(userAccount.userId)&&transact.accountId.equals(userAccount.AccountId) ) {
				ua.AccountId = transact.accountId;
				ua.userId = transact.toUserId;
			}
		}
		return ua;
	}

	public static Store depositActions(Store store, Transaction transact) {
		Account newDepositAccount = BankOperations.deposit(transact.accountId,
				transact.amount, store.accounts);
		if (newDepositAccount != null) {
			for (Account acc : store.accounts) {
				if (newDepositAccount.accountId.equals(acc.accountId)) {
					acc = newDepositAccount;
				}
			}
		}
		return store;
	}

	public static Store withdrawalActions(Store store, Transaction transact) {
		Account newAccount = BankOperations.withdraw(transact.accountId,
				transact.amount, store.accounts, transact.userId, store.userAccounts);
		if (newAccount != null) {
			for (Account acc : store.accounts) {
				if (newAccount.accountId.equals(acc.accountId)) {
					acc = newAccount;
				}
			}

		}
		return store;
	}

	public static Store transferFundsAction(Store store, Transaction transact) {
		Account fromAccount = BankOperations.withdraw(transact.toAccountId, transact.amount, store.accounts, transact.userId, store.userAccounts);
		Account toAccount = BankOperations.deposit(transact.accountId, transact.amount,
				store.accounts);
		if (fromAccount != null && toAccount != null) {
			for (Account acc : store.accounts) {
				if (fromAccount.accountId.equals(acc.accountId)) {
					acc = fromAccount;
				}
				if (toAccount.accountId.equals(acc.accountId)) {
					acc = toAccount;
				}
			}
		}
		return store;
	}

	public static Store addAccountAction(Store store, Transaction transact) {
		Account acct = BankOperations.openAccount();
		store.userAccounts.add(BankOperations.linkAccount(acct.accountId, transact.userId));
		store.accounts.add(acct);
		store.transactions.add(transact);
		return store;
	}

	public static boolean checkOwnership(CopyOnWriteArrayList<UserAccount> userAccounts, String acct, String user) {
		boolean ownership = false;
		for(UserAccount ua: userAccounts) {
			if(ua.AccountId.equals(acct)&&ua.userId.equals(user)) {
				ownership = true;
			}
		}
		
		if(ownership==false) {
			System.out.println("You don't own that account or the account does not exist");
		}
		return ownership;
	}
}
