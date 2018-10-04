package com.revature.BankingApp;

import java.util.ArrayList;

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
		// Initialize State load files, if it exists, if not, new instance
		// of each arraylist
		
		Store store = Services.readToObj("store");
		ArrayList<User >users = store.users;
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
		}
		
		Services.clearTerminal();
		
		// the user screen, after authentication
		switch (Screen.userScreen(loggedInUser)) {
		case "0":
			BankOperations.logout(store);
			break;
		case "1":
			if (Screen.addAccountScreen().contentEquals("y")) {
				Account acct = BankOperations.openAccount();
				BankOperations.linkAccount(acct.accountId, loggedInUser.userId);
				Services.writeToFile(store, "store");
			}
			break;
		case "2":
			Screen.viewAccounts(accounts);
			break;
		case "3":
			Screen.transferFunds();
		case "4":
			Screen.withdrawFunds();
		case "5":
			Screen.depositFunds();
		}

	}
}
