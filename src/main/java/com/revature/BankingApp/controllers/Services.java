package com.revature.BankingApp.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.revature.BankingApp.models.Account;
import com.revature.BankingApp.models.Store;
import com.revature.BankingApp.models.Transaction;
import com.revature.BankingApp.models.User;
import com.revature.BankingApp.models.UserAccount;

public class Services {
	public static String cliInput(String msg) {
		Scanner sc = new Scanner(System.in);
		System.out.print(msg);
		String input = sc.nextLine();

//		sc.close();
		return input;
	}

	public static String writeToFile(Object obj, String toFile) {
		File checkFile = new File(toFile);
		if(checkFile.exists()) {
			checkFile.delete();
		}
		
		try (ObjectOutputStream oos = 
				new ObjectOutputStream(
						new FileOutputStream(toFile))) {
			oos.writeObject(obj);
			return "Success";
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return "Error has occurred";
	}

	public static Store readToObj(String fromFile) {
		Store obj = null;
		try (ObjectInputStream ois = 
				new ObjectInputStream(
						new FileInputStream(fromFile))) {
			obj =  (Store) ois.readObject();
		} catch (ClassNotFoundException e) {
			Store store = new Store();
			store.accounts = new ArrayList<Account>();
			store.transactions = new ArrayList<Transaction>();
			store.users = new ArrayList<User>();
			store.userAccounts = new ArrayList<UserAccount>();
			return store;
		} catch (FileNotFoundException ex) {
			Store store = new Store();
			store.accounts = new ArrayList<Account>();
			store.transactions = new ArrayList<Transaction>();
			store.users = new ArrayList<User>();
			store.userAccounts = new ArrayList<UserAccount>();
			return store;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		if (obj.accounts == null) {
			obj.accounts = new ArrayList<Account>();
		}
		if (obj.transactions == null) {
			obj.transactions = new ArrayList<Transaction>();
		}
		if (obj.users == null) {
			obj.users = new ArrayList<User>();
		}
		if (obj.userAccounts == null) {
			obj.userAccounts = new ArrayList<UserAccount>();
		}
		return obj;
	}

	public static void clearTerminal() {
	}

	public static ArrayList<User> initialize(ArrayList<User> users) {
		for(User user: users) {
			if(user.userName.equals("SuperAdmin")) {
				return users;
			}
		}
		
		User admin = new User();
		admin.role = "admin";
		admin.userId = "007";
		admin.userName="SuperAdmin";
		admin.password = "password";
		users.add(admin);
		
		return users;
	}

}