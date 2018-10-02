package com.revature.BankingApp.view;

import java.util.UUID;
import com.revature.BankingApp.controllers.Services;
import com.revature.BankingApp.models.Accounts;
import com.revature.BankingApp.models.User;

public class Screen {
	public void splash() {
		System.out.println("Welcome to the Banking App");
	}
	
	public String loginScreen() {
		System.out.println(	"Please make a selection: "+ '\n'+
							"0.  Logout"+'\n' +
							"1.  Register New User" +'\n' +
							"2.  Login Existing User"+'\n');
		String choice = Services.cliInput("Entry => ");
		
		if(!choice.equals("0")||!choice.equals("1")||!choice.equals("2")) {
			System.out.println("Please make a valid selection");
			loginScreen();
		}
		
		return choice;
	}
	
	public String userScreen() {
		System.out.print("What would like to do today?"+ '\n'+
				"0.  Logout"+'\n'+
				"1.  Open new Account"+ '\n'+
				"2.  View Accounts '\n'");
		String choice = Services.cliInput("Entry=> ");
		return choice;
	}
	
	public String addAccountScreen() {
		System.out.println("Would you like to add an account?);");
		String choice = Services.cliInput("Entry=> ");
		return choice;
	}
	
	public String addAnotherAccountScreen() {
		System.out.println("Would you like to add another account?);");
		String choice = Services.cliInput("Entry=> ");
		return choice;
	}
	
	public Object registration() {
		String userId=UUID.randomUUID().toString(); 
		String userName;
		String role; 
		
		System.out.println("User Registration:");
		userName = Services.cliInput("Enter a username: ");
		role = Services.cliInput("Enter a role, for example, customer:  ");
		User user =new User(userId, userName, role);
		return user;
	}
	
	public void viewAccounts(Accounts accounts) {
		for(String object: accounts) {
			
		}
	}

}
