package com.revature.BankingApp.view;

import java.util.ArrayList;

import com.revature.BankingApp.controllers.BankOperations;
import com.revature.BankingApp.controllers.Services;
import com.revature.BankingApp.models.Account;
import com.revature.BankingApp.models.User;

public class Screen {
	public static void splash() {
		System.out.println("Welcome to the Banking App");
	}

	public static String loginScreen() {
		System.out.println("Please make a selection: ");
		System.out.println("0.  Logout");
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
		String choice = Services.cliInput("Entry=> ");
		return choice;
	}

	public static String accountOperations(Account account) {
		System.out.println("Account: ");
		System.out.println("0.  Exit");
		System.out.println("1.  Deposit");
		System.out.println("2.  Withdrawal");
		System.out.println("3.  Transfer");
		String choice = Services.cliInput("Entry => ");
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

	public static User registration() {
		String userName;
		String password = "0";
		String password2 = "2";

		System.out.println("User Registration:");
		userName = Services.cliInput("Enter a username: ");

		while (!password.equals(password2)) {
			password = Services.cliInput("Please Enter a Password=> ");
			password2 = Services.cliInput("Please Verify your Password => ");
		}

		User user = BankOperations.addNewUser(userName, password);
		return user;
	}

	public static void viewAccounts(ArrayList<Account> accounts) {
		System.out.println("AcctID              Balance");
		int counter = 1;
		for (Account object : accounts) {
			counter = counter++;
			System.out.println(counter + ". " + object.accountId + "         " + object.balance);

		}
	}

	public static void transferFunds() {
		System.out.println("Transfer Funds:");
		String accountId = Services.cliInput("Transfer to Which Account? =>");
		String amount = Services.cliInput("How much to transfer? =>");
		
	}

	public static void withdrawFunds() {
		System.out.println("Withdraw Funds:");
		String accountId = Services.cliInput("Transfer to Which Account? =>");
		String amount = Services.cliInput("How much to transfer? =>");
		
	}

	public static void depositFunds() {
		System.out.println("Deposit Funds:");
		String accountId = Services.cliInput("Transfer to Which Account? =>");
		String amount = Services.cliInput("How much to transfer? =>");
		
	}

}
