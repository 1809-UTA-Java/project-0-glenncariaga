package com.revature.BankingApp;

import java.util.concurrent.CopyOnWriteArrayList;

import com.revature.BankingApp.controllers.Services;
import com.revature.BankingApp.models.Account;
import com.revature.BankingApp.models.User;
import com.revature.BankingApp.models.UserAccount;

public class Admin {

	public static void viewUsers(CopyOnWriteArrayList<User> users) {
		System.out.println("Users:");
		System.out.println("User ID                                UserName   Role  ");
		for (User user : users) {
			System.out.println(user.userId + "  " + user.userName + "   " + user.role);
		}
	}

	public static void viewAccounts(CopyOnWriteArrayList<Account> accounts) {
		System.out.println("Accounts");
		System.out.println("Account ID                              Balance         Open?");
		for (Account account : accounts) {
			System.out.println(account.accountId + "  " + account.balance + "  " + account.open);
		}
	}

	public static void viewUserAccounts(CopyOnWriteArrayList<User> users, CopyOnWriteArrayList<Account> accounts,
			CopyOnWriteArrayList<UserAccount> userAccounts) {
		System.out.println("Report");
		System.out.println(
				"  User ID                               UserName Role      Account ID                              Balance        Open?");
		Boolean userFound = false;
		Boolean accountFound = false;
		for (UserAccount userAccount : userAccounts) {
			for (User user : users) {
				if (userAccount.userId.equals(user.userId)) {
					System.out.print(user.userId + "  " + user.userName + "  " + user.role + "  ");
					userFound = true;
					break;
				}

			}
			if (!userFound) {
				System.out.print("========No User found=========");
			} else {
				userFound = false;
			}

			if (userFound) {

			}
			for (Account account : accounts) {
				if (userAccount.AccountId.equals(account.accountId)) {
					System.out.print(account.accountId + "  " + account.balance + "  " + account.open);
					accountFound = true;
				}
			}
			if (!accountFound) {
				System.out.print("================No Account found======================");
			} else {
				accountFound = false;
			}
			System.out.print('\n');
		}

	}

	public static User editUser(CopyOnWriteArrayList<User> users) {
		System.out.println("Edit User Info");
		User edit = new User();
		boolean valid = false;
		edit.userId = Services.cliInput("Enter UserId to edit =>");
		for (User user : users) {
			if (user.userId.equals(edit.userId)) {
				valid = true;
				edit = user;
				
				System.out.println("User found!");
			}
		}
		if (!valid) {
			System.out.println("Please enter a valid ID!");
			return null;
		}
		String _userName = Services.cliInput("User Name: " + edit.userName + " => ");
		if (!_userName.isEmpty()) {
			edit.userName = _userName;
		}
		
		String _role = Services.cliInput("User Role: " + edit.role + " => ");
		if (!_role.isEmpty()) {
			edit.role = _role;
		}
		String _password = Services.cliInput("User Password: " + edit.password + " => ");
		if (!_password.isEmpty()) {
			edit.password = _password;
		}
		System.out.println(edit.role);
		return edit;
	}

	public static Account editAccount(CopyOnWriteArrayList<Account> accounts) {
		System.out.println("Edit User Info");
		Account edit = new Account();
		boolean valid = false;
		edit.accountId = Services.cliInput("Enter UserId to edit =>");
		for (Account account : accounts) {
			if (account.accountId.equals(edit.accountId)) {
				valid = true;
				edit = account;
			}
		}
		if (!valid) {
			System.out.println("Please enter a valid ID!");
			return null;
		}
		String _balance = Services.cliInput("Current Balance = " + edit.balance + " => ");
		if (!_balance.isEmpty()) {
			edit.balance = Float.parseFloat(_balance);
		}
		String _open = Services.cliInput("Account open = " + edit.open + " => ");
		if (!_open.isEmpty()) {
			edit.open = Boolean.parseBoolean(_open);
		}

		return edit;
	}

}
