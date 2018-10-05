package com.revature.BankingApp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
		/**
		 * 
		 * Store: is the source of all truth. It is here that data is stored, updated,
		 * and kept current. The state of the program is also initialized here.
		 * 
		 * Navigation: to move from screen to screen, blocks of code are wrapped in
		 * while loops that ends when navigation changes. All the blocks are wrapped
		 * around a larger loop called "menu." breaking out of menu ends the program,
		 * though preferably, user logs out so the store is saved to file.
		 */
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
		/**
		 * 
		 */
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
				case "1": // Add Account
					/**
					 * Creation of an account is a 3 step process: 1) a unique id is created by the
					 * Account, balance set to 0 2) a transaction object is created, requesting
					 * approval 3) an object with accountId and userId is created in the UserAccount
					 * collection
					 * 
					 */
					if (Screen.addAccountScreen().contentEquals("y")) {
						// creation of collection needed to create a Transaction object
						HashMap<String, String> config = new HashMap<>();
						config.put("userId", loggedInUser.userId);
						config.put("action", "addAccount");
						// creation of Transaction object
						Transaction addAccount = BankOperations.requestApproval(config);
						// if certain criteria is met, quick approval.
						Transaction quickApprove = BankOperations.quickApproval(addAccount, store);

						// if approved, a Transaction object is returned, otherwise, null is returned
						if (quickApprove != null) {
							Account acct = BankOperations.openAccount();
							userAccounts.add(BankOperations.linkAccount(acct.accountId, loggedInUser.userId));
							accounts.add(acct);
							Services.writeToFile(store, "store");
							System.out.println("Account created: " + acct.accountId);
							System.out.println("");
							addAccount = quickApprove;
							Services.writeToFile(store, "store");
						}
						// saving the information to store
						transactions.add(addAccount);
						Services.writeToFile(store, "store");
					}

					break;
				case "2": // View Accounts
					Screen.viewAccounts(accounts, userAccounts, loggedInUser);
					break;
				case "3": // Transfer Funds
					/**
					 * Transfer Funds is accomplished by: 1) Doing a withdrawal 2) Doing a deposit.
					 * 
					 * The user needs to be careful of the values being entered for accounts.
					 */
					// returns an array with [0]the accountId money is coming from
					// [1] the accountId is going to
					// [2] the amount that is being transfered.
					String[] transferFunds = Screen.transferFunds(accounts);
					HashMap<String, String> config = new HashMap<>();
					config.put("accountId", transferFunds[1]);
					config.put("toAccountId", transferFunds[0]);
					config.put("userId", loggedInUser.userId);
					config.put("amount", transferFunds[2]);
					config.put("action", "transferFunds");

					Transaction reqTransferFunds = BankOperations.requestApproval(config);
					Transaction quickApprove = BankOperations.quickApproval(reqTransferFunds, store);

					if (quickApprove != null) {
						Account fromAccount = BankOperations.withdraw(transferFunds[1],
								Float.parseFloat(transferFunds[2]), accounts, loggedInUser.userId, userAccounts);
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
							System.out.println("Funds transfered");
						}
						reqTransferFunds = quickApprove;
					}

					transactions.add(reqTransferFunds);
					Services.writeToFile(store, "store");
					break;

				case "4": // Withdrawals
					// returns an array with two values: [0] is the accountId, [1] is the amount
					String[] withdrawFunds = Screen.withdrawFunds(accounts);

					HashMap<String, String> config1 = new HashMap<>();
					config1.put("accountId", withdrawFunds[0]);
					config1.put("amount", withdrawFunds[1]);
					config1.put("action", "withdraw");

					Transaction reqWithdrawal = BankOperations.requestApproval(config1);
					Transaction reqQuickApprove = BankOperations.quickApproval(reqWithdrawal, store);

					if (reqQuickApprove != null) {
						Account newAccount = BankOperations.withdraw(withdrawFunds[0],
								Float.parseFloat(withdrawFunds[1]), accounts, loggedInUser.userId, userAccounts);
						if (newAccount != null) {
							for (Account acc : accounts) {
								if (newAccount.accountId.equals(acc.accountId)) {
									acc = newAccount;
								}
							}

						}
						reqWithdrawal = reqQuickApprove;
					}

					transactions.add(reqWithdrawal);
					Services.writeToFile(store, "store");
					break;

				case "5": // Deposits
					// returns an array: [0] is accountId [1]amount
					String[] depositFunds = Screen.depositFunds(accounts);

					HashMap<String, String> config2 = new HashMap<>();
					config2.put("accountId", depositFunds[0]);
					config2.put("amount", depositFunds[1]);
					config2.put("action", "deposit");

					Transaction reqDepositFunds = BankOperations.requestApproval(config2);
					Transaction quickDepositApprove = BankOperations.quickApproval(reqDepositFunds, store);
					if (quickDepositApprove != null) {
						Account newDepositAccount = BankOperations.deposit(depositFunds[0],
								Float.parseFloat(depositFunds[1]), accounts);
						if (newDepositAccount != null) {
							for (Account acc : accounts) {
								if (newDepositAccount.accountId.equals(acc.accountId)) {
									acc = newDepositAccount;
								}
							}
						}
						reqDepositFunds = quickDepositApprove;
					}
					transactions.add(reqDepositFunds);
					Services.writeToFile(store, "store");
					break;
				case "6":
					break;
				}// end of switch case
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
				}// end of switch case
			} // end of adminScreen

		} // end of outer container
		Scanner sc = new Scanner(System.in);
		sc.close();
	}
}
