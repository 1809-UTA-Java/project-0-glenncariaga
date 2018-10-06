package com.revature.BankingApp.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import com.revature.BankingApp.controllers.BankOperations;
import com.revature.BankingApp.controllers.Services;
import com.revature.BankingApp.models.Account;
import com.revature.BankingApp.models.Store;
import com.revature.BankingApp.models.Transaction;
import com.revature.BankingApp.models.User;
import com.revature.BankingApp.models.UserAccount;

public class Screen {
	public static void splash() {
		System.out.println("Welcome to the Banking App");
	}

	public static String loginScreen() {
		System.out.println("Please make a selection: ");
		System.out.println("0.  Exit Program");
		System.out.println("1.  Register New User");
		System.out.println("2.  Login Existing User");
		String choice = Services.cliInput("Entry => ");
		return choice;
	}

	public static String[] authentication() {
		String[] usrPswd = new String[2];
		System.out.println("Please Log in");
		usrPswd[0] = Services.cliInput("Please Enter a Username: ");
		usrPswd[1] = Services.cliInput("Enter a password: ");
		return usrPswd;
	}

	public static String userScreen(User user) {
		System.out.println("Welcome " + user.userName + ", what would like to do today?");
		System.out.println("0.  Logout");
		System.out.println("1.  Open new Account");
		System.out.println("2.  View Accounts");
		System.out.println("3.  Transfer Funds to another account");
		System.out.println("4.  Withdraw Funds from an account");
		System.out.println("5.  Deposit Funds to an account.");
		String choice = Services.cliInput("Entry=> ");
		return choice;
	}

	public static String addAccountScreen() {
		System.out.println("Would you like to add an account?);");
		String choice = Services.cliInput("Entry=> ");
		return choice;
	}

	public static String addAnotherAccountScreen() {
		System.out.println("Would you like to add another account?);");
		String choice = Services.cliInput("Entry=> ");
		return choice;
	}

	public static User registration(CopyOnWriteArrayList<User> users) {
		String userName = new String();
		String password = "0";
		String password2 = "2";

		System.out.println("User Registration:");
		do {
			userName = Services.cliInput("Enter a username: ");
		} while (BankOperations.checkUserNameDup(users, userName));
		while (!password.equals(password2)) {
			password = Services.cliInput("Please Enter a Password=> ");
			password2 = Services.cliInput("Please Verify your Password => ");
		}

		User user = BankOperations.addNewUser(userName, password);
		return user;
	}

	public static void viewAccounts(CopyOnWriteArrayList<Account> accounts, CopyOnWriteArrayList<UserAccount> userAccounts, User user) {
		ArrayList<Account> ownedAccounts = new ArrayList<Account>();
		for (Account account : accounts) {
			for (UserAccount linked : userAccounts) {
				if (account.accountId.equals(linked.AccountId) && linked.userId.equals(user.userId)) {
					ownedAccounts.add(account);
				}
			}
		}
		System.out.println("Accounts related to this User: " + ownedAccounts.size());
		System.out.println("AcctID                                      Balance");
		int counter = 1;
		for (Account object : ownedAccounts) {
			counter = counter++;
			System.out.println(counter + ". " + object.accountId + "         " + object.balance);

		}
		Services.cliInput("");
	}

	public static String[] transferFunds(CopyOnWriteArrayList<Account> accounts) {
		System.out.println("Transfer Funds:");
		String fromAccountId = new String();
		String toAccountId = new String();
		do {
			fromAccountId = Services.cliInput("Transfer FROM which Account? => ");
		}while (BankOperations.checkAccountId(accounts, fromAccountId));
		do {
			toAccountId = Services.cliInput("Transfer TO Which Account? => ");
		}while (BankOperations.checkAccountId(accounts, toAccountId));

		String amount = Services.cliInput("How much to transfer? =>");
		String[] list = { toAccountId, fromAccountId, amount };
		return list;
	}

	public static String[] withdrawFunds(CopyOnWriteArrayList<Account> accounts) {
		System.out.println("Withdraw Funds:");
		String accountId = new String();
		do {
			accountId = Services.cliInput("Withdraw from which Account? =>");
		} while (BankOperations.checkAccountId(accounts, accountId));
		String amount = Services.cliInput("How much to withdraw? =>");
		String[] list = { accountId, amount };
		return list;
	}

	public static String[] depositFunds(CopyOnWriteArrayList<Account> accounts) {
		System.out.println("Deposit Funds:");
		String accountId = new String();
		do {
			accountId = Services.cliInput("Deposit to which Account? =>");
		} while (BankOperations.checkAccountId(accounts, accountId));
		String amount = Services.cliInput("How much to deposit? =>");
		String[] list = { accountId, amount };
		return list;

	}

	public static String adminScreen() {
		System.out.println("Admin Controls:");
		System.out.println("0.  Logout");
		System.out.println("1.  View Users");
		System.out.println("2.  View Accounts");
		System.out.println("3.  View Users/Accounts");
		System.out.println("4.  Edit User");
		System.out.println("5.  Edit Account");
		System.out.println("6.  Process Pending Transactions");
		String choice = Services.cliInput("Make a selection => ");
		return choice;
	}

	public static boolean approveTransaction(Transaction transact) {
		
		System.out.print(
		"UserId: " + transact.userId+ '\n'+
		"AccountId: " + transact.accountId+ '\n'+
		"Transfer AccountId: "+transact.toAccountId+ '\n'+
		"Action: "+transact.action+ '\n'+
		"Amount: "+ transact.amount+ '\n'+
		"Date: "+transact.date+ '\n'+'\n');
		
		String choice = Services.cliInput("Approve Transaction? (y/n) =>");
		if(choice.equals("y")) {
			return true;
		}
		return false;
	}
	
	public static String employeeScreen() {
		String choice;
		System.out.println("Employee Screen");
		System.out.println("0.  Logout");
		System.out.println("1.  View Users");
		System.out.println("2.  View Accounts");
		System.out.println("3.  View UserAccounts");
		System.out.println("4.  Process Pending Transactions");
		choice = Services.cliInput("Entry => ");
		return choice;
	}
}
