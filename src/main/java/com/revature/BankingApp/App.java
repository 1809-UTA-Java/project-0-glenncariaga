package com.revature.BankingApp;

import java.io.InputStream;
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

		// a welcome screen
		Screen.splash();
		// the login screen.

		boolean authenticate = false;
		while (!authenticate) {

			switch (Screen.loginScreen()) {
			case "0":
				BankOperations.logout(store);
				break;
			case "1":
				User user = Screen.registration();
				users.add(user);
				break;

			default:

			}

			Services.clearTerminal();

			// this is the screen to login a user

			int failedTries = 0;

			while (!authenticate) {
				String[] usrPswd = Screen.authentication();
				authenticate = BankOperations.authenticate(usrPswd, users);
				if (!authenticate) {
					System.out.println("Authentication Failed.");
				}
				failedTries = failedTries + 1;
				if (failedTries > 3) {
					BankOperations.logout(store);
					break;
				}
				if (authenticate) {
					loggedInUser = BankOperations.getUserInfo(usrPswd[0], users);
				}
			}
		}//end of loginScreen

		Services.clearTerminal();

		// the user screen, after authentication
		boolean userScreen = false;
		
		while (!userScreen) {
			switch (Screen.userScreen(loggedInUser)) {
			case "0":
				BankOperations.logout(store);
				break;
			case "1":
				if (Screen.addAccountScreen().contentEquals("y")) {
					Account acct = BankOperations.openAccount();
					userAccounts.add(BankOperations.linkAccount(acct.accountId, loggedInUser.userId));
					accounts.add(acct);
					Services.writeToFile(store, "store");
				}
				break;
			case "2":
				Screen.viewAccounts(accounts,userAccounts, loggedInUser);
				break;
			case "3":
				String[] transferFunds =
				Screen.transferFunds();
				break;
				
			case "4":
				String[] withdrawFunds =
					Screen.withdrawFunds();
				Account newAccount = BankOperations.withdraw(withdrawFunds[0],Float.parseFloat( withdrawFunds[1]), accounts, loggedInUser.userId, userAccounts);
				if(newAccount != null) {
					for(Account acc: accounts) {
						if(newAccount.accountId.equals(acc.accountId)) {
							acc = newAccount;
						}
					}
				}
				Services.writeToFile(store, "store");
				break;
				
			case "5":
				String[] depositFunds = 
					Screen.depositFunds();
				Account newDepositAccount = BankOperations.deposit(depositFunds[0], Float.parseFloat(depositFunds[1]), accounts);
				if(newDepositAccount != null) {
					for(Account acc: accounts) {
						if(newDepositAccount.accountId.equals(acc.accountId)) {
							acc = newDepositAccount;
						}
					}
				}
				break;
			}
		}// end of userScreen switch case

		Scanner sc = new Scanner(System.in);
		sc.close();
	}
}
