package com.revature.BankingApp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Store implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 791907848829295773L;
	public CopyOnWriteArrayList<User> users;
	public CopyOnWriteArrayList<Account> accounts;
	public CopyOnWriteArrayList<Transaction> transactions;
	public CopyOnWriteArrayList<UserAccount> userAccounts;
}
