package com.revature.BankingApp;

import java.util.ArrayList;

import com.revature.BankingApp.controllers.Services;
import com.revature.BankingApp.models.Account;
import com.revature.BankingApp.models.User;
import com.revature.BankingApp.models.UserAccount;

public class Admin {

	public static void viewUsers(ArrayList<User> users) {
		System.out.println("Users:");
		System.out.println("User ID                                UserName   Role  ");
		for(User user: users) {
			System.out.println(user.userId+"  "+user.userName+"   "+user.role);
		}
	}

	public static void viewAccounts(ArrayList<Account> accounts) {
		System.out.println("Accounts");
		System.out.println("Account ID                              Balance         Open?");
		for(Account account: accounts) {
			System.out.println(account.accountId+"  "+account.balance+"  "+account.open);
		}
	}

	public static void viewUserAccounts(ArrayList<User> users, ArrayList<Account> accounts,
			ArrayList<UserAccount> userAccounts) {
		System.out.println("Report");
		System.out.println("  User ID                               UserName Role      Account ID                              Balance        Open?");
		Boolean userFound = false;
		Boolean accountFound = false;
		for(UserAccount userAccount: userAccounts) {
			for(User user: users) {
				if(userAccount.userId.equals(user.userId)) {
					System.out.print(user.userId+"  "+user.userName+"  "+user.role+"  ");
					userFound = true;
					break;
				}
				
			}
			if(!userFound){
				System.out.print("========No User found=========");
			}else {
				userFound = false;
			}
			
			if(userFound) {
				
			}
			for(Account account: accounts) {
				if(userAccount.AccountId.equals(account.accountId)) {
					System.out.print(account.accountId+"  "+account.balance+"  "+account.open);
					accountFound = true;
				}
			}
			if(!accountFound){
				System.out.print("================No Account found======================");
			}else {
				accountFound = false;
			}
			System.out.print('\n');
		}
		
	}

	public static User editUser(ArrayList<User> users) {
		System.out.println("Edit User Info");
		User edit = new User();
		User oldUser = new User();
		boolean valid = false;
		edit.userId = Services.cliInput("Enter UserId to edit =>");
		for(User user:users) {
			if(user.userId.equals(edit.userId)){
				valid = true;
				oldUser = user;
			}
		}
		if(!valid) {
			System.out.println("Please enter a valid ID!");
			return null;
		}
		
		edit.userName =Services.cliInput("User Name"+oldUser.userName+" => ");
		edit.role = Services.cliInput("User Role" + oldUser.role+" => ");
		edit.password = Services.cliInput("User Password"+oldUser.password+" => ");

		return edit;		
	}

	public static Account editAccount(ArrayList<Account> accounts) {
		System.out.println("Edit User Info");
		Account edit = new Account();
		Account oldAcct = new Account();
		boolean valid = false;
		edit.accountId = Services.cliInput("Enter UserId to edit =>");
		for(Account account:accounts) {
			if(account.accountId.equals(edit.accountId)){
				valid = true;
				oldAcct = account;
			}
		}
		if(!valid) {
			System.out.println("Please enter a valid ID!");
			return null;
		}
		
		edit.balance =Float.parseFloat(Services.cliInput("Current Balance = "+oldAcct.balance+" => "));
		edit.open = Boolean.parseBoolean(Services.cliInput("Account open = "+oldAcct.open +" => "));

		return edit;	
	}

}
