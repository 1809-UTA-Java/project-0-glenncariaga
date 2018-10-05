package com.revature.BankingApp;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.BankingApp.controllers.BankOperations;
import com.revature.BankingApp.controllers.Services;
import com.revature.BankingApp.models.Account;
import com.revature.BankingApp.models.Store;
import com.revature.BankingApp.models.Transaction;
import com.revature.BankingApp.models.User;
import com.revature.BankingApp.models.UserAccount;
import com.revature.BankingApp.view.Screen;

/**
 *
 *
 */
public class App {

	public static void main(String[] args) {

		Store store = Services.readToObj("store");
		ArrayList<User> users = store.users;
		ArrayList<Account> accounts = store.accounts;
		ArrayList<Transaction> transactions = store.transactions;
		ArrayList<UserAccount> userAccounts = store.userAccounts;
		User loggedInUser = new User();// tracking who is logged in.
		String navigation = "menu"; // flag to allow navigation of program

		users = Services.initialize(users);// creates SuperAdmin

		// a welcome screen
		Screen.splash();
		// the login screen.
		while (navigation == "menu") {// outer container
			navigation = "login";
			while (navigation == "login") {

				switch (Screen.loginScreen()) {
				case "0":
					BankOperations.logout(store);
					break;
				case "1":
					User user = Screen.registration(users);
					users.add(user);
					navigation = "authenticate";
					break;
				case "2":
					navigation = "authenticate";
					break;
				}

				Services.clearTerminal();

				// this is the screen to login a user
				int failedTries = 0;

				while (navigation == "authenticate") {
					String[] usrPswd = Screen.authentication();
					if (BankOperations.authenticate(usrPswd, users)) {
						navigation = "userScreen";
					}
					if (navigation != "userScreen") {
						System.out.println("Authentication Failed.");
					}
					failedTries = failedTries + 1;
					if (failedTries > 2) {
						BankOperations.logout(store);
						break;
					}
					if (navigation == "userScreen") {
						loggedInUser = BankOperations.getUserInfo(usrPswd[0], users);
						if (loggedInUser.role.equals("admin")) {
							navigation = "adminScreen";
						}
					}
				}
			} // end of loginScreen

			Services.clearTerminal();

			// the user screen, after authentication

			while (navigation == "userScreen") {
				switch (Screen.userScreen(loggedInUser)) {
				case "0":
					navigation = "menu";
					loggedInUser = null;
					break;
				case "1": //Add Account
					if (Screen.addAccountScreen().contentEquals("y")) {
						Account acct = BankOperations.openAccount();
						userAccounts.add(BankOperations.linkAccount(acct.accountId, loggedInUser.userId));
						accounts.add(acct);
						Services.writeToFile(store, "store");
						System.out.println("Account created: " + acct.accountId);
						System.out.println("");
						Services.writeToFile(store, "store");
					}
					break;
				case "2": //View Accounts
					Screen.viewAccounts(accounts, userAccounts, loggedInUser);
					break;
				case "3": //Transfer Funds
					String[] transferFunds = Screen.transferFunds(accounts);
					Account fromAccount = BankOperations.withdraw(transferFunds[1], Float.parseFloat(transferFunds[2]),
							accounts, loggedInUser.userId, userAccounts);
					Account toAccount = BankOperations.deposit(transferFunds[0], Float.parseFloat(transferFunds[2]),
							accounts);
					if (fromAccount != null && toAccount != null) {
						for (Account acc : accounts) {
							if (fromAccount.accountId.equals(acc.accountId)) {
								acc = fromAccount;
							}
							if (toAccount.accountId.equals(acc.accountId)) {
								acc = toAccount;
							}
						}
						Services.writeToFile(store, "store");
						System.out.println("Funds transfered");
					}
					break;

				case "4": //Withdrawals
					String[] withdrawFunds = Screen.withdrawFunds(accounts);
					Account newAccount = BankOperations.withdraw(withdrawFunds[0], Float.parseFloat(withdrawFunds[1]),
							accounts, loggedInUser.userId, userAccounts);
					if (newAccount != null) {
						for (Account acc : accounts) {
							if (newAccount.accountId.equals(acc.accountId)) {
								acc = newAccount;
							}
						}
						Services.writeToFile(store, "store");
					}

					break;

				case "5": //Deposits
					String[] depositFunds = Screen.depositFunds(accounts);
					Account newDepositAccount = BankOperations.deposit(depositFunds[0],
							Float.parseFloat(depositFunds[1]), accounts);
					if (newDepositAccount != null) {
						for (Account acc : accounts) {
							if (newDepositAccount.accountId.equals(acc.accountId)) {
								acc = newDepositAccount;
							}
						}
					}
					break;
				case "6":
					break;
				}//end of switch case
			} // end of userScreen switch case

			while (navigation == "adminScreen") {
				switch (Screen.adminScreen()) {
				case "0":
					navigation = "menu";
					break;
				case "1":
					Admin.viewUsers(users);
					break;
				case "2":
					Admin.viewAccounts(accounts);
					break;
				case "3":
					Admin.viewUserAccounts(users, accounts, userAccounts);
					break;
				case "4":
					User editUser = Admin.editUser(users);
					if (editUser != null) {
						for (User user : users) {
							if (user.userId.equals(editUser.userId)) {
								user = editUser;

							}
						}
						Services.writeToFile(store, "store");
					}
					break;
				case "5":
					Account editAccount = Admin.editAccount(accounts);
					if (editAccount != null) {
						for (Account account : accounts) {
							if (account.accountId.equals(editAccount.accountId)) {
								account = editAccount;
							}
						}
					}
					Services.writeToFile(store, "store");
					break;
				}//end of switch case
			}//end of adminScreen

		} // end of outer container
		Scanner sc = new Scanner(System.in);
		sc.close();
	}
}
