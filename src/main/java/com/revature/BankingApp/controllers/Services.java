package com.revature.BankingApp.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import com.revature.BankingApp.models.Account;
import com.revature.BankingApp.models.Store;
import com.revature.BankingApp.models.Transaction;
import com.revature.BankingApp.models.User;
import com.revature.BankingApp.models.UserAccount;
import com.revature.BankingApp.util.ConnectionUtil;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

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
			store.accounts = new CopyOnWriteArrayList<Account>();
			store.transactions = new CopyOnWriteArrayList<Transaction>();
			store.users = new CopyOnWriteArrayList<User>();
			store.userAccounts = new CopyOnWriteArrayList<UserAccount>();
			return store;
		} catch (FileNotFoundException ex) {
			Store store = new Store();
			store.accounts = new CopyOnWriteArrayList<Account>();
			store.transactions = new CopyOnWriteArrayList<Transaction>();
			store.users = new CopyOnWriteArrayList<User>();
			store.userAccounts = new CopyOnWriteArrayList<UserAccount>();
			return store;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		if (obj.accounts == null) {
			obj.accounts = new CopyOnWriteArrayList<Account>();
		}
		if (obj.transactions == null) {
			obj.transactions = new CopyOnWriteArrayList<Transaction>();
		}
		if (obj.users == null) {
			obj.users = new CopyOnWriteArrayList<User>();
		}
		if (obj.userAccounts == null) {
			obj.userAccounts = new CopyOnWriteArrayList<UserAccount>();
		}
		return obj;
	}

	public static void clearTerminal() {
	}

	public static CopyOnWriteArrayList<User> initialize(CopyOnWriteArrayList<User> users) {
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
	
	public static ResultSet selectAll(String sql, String tableName) throws IOException {
		ResultSet rs = null;
		try (Connection conn= ConnectionUtil.getConnection()){
            //Register the driver
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

            //Create Statement
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(); 
            conn.close();
        } catch (SQLException ex) {
            ex.getMessage();
        } 
		return rs;
	}
	public static CopyOnWriteArrayList<User> getAllUsers() {
		CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<User>();
		try(Connection conn = ConnectionUtil.getConnection()){
			CallableStatement stmt = conn.prepareCall("BEGIN GETALLUSERS(?);END;");
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.execute();
			ResultSet rs = ((OracleCallableStatement)stmt).getCursor(1);
			while(rs.next()) {
				User user = new User();
				user.userId = rs.getString("ID");
				user.userName = rs.getString("USERNAME");
				user.password = rs.getString("PASSWORD");
				user.role = rs.getString("ROLE");
				users.add(user);
			}
			conn.close();
			stmt.close();
		}catch (SQLException | IOException ex) {
			ex.getMessage();
		}
		return users;
	}
	public static void insertUser(User user) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			CallableStatement stmt = conn.prepareCall("BEGIN INSERTUSER(?,?,?,?); END;" );
			stmt.setString(1, user.userId);
			stmt.setString(2, user.userName);
			stmt.setString(3, user.password);
			stmt.setString(4, user.role);
			stmt.execute();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}