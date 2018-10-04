package com.revature.BankingApp.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {
	private static final long serialVersionUID = 791907848829295773L;
	public ArrayList<User> users;
	public ArrayList<Account> accounts;
	public ArrayList<Transaction> transactions;
	public ArrayList<UserAccount> userAccounts;
}
