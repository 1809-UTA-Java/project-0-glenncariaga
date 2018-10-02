package com.revature.BankingApp.models;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String UserId;
	private String UserName;
	private String role;
	private ArrayList<String> linkedAccounts;

	public User(String userId, String userName, String role) {
		super();
		UserId = userId;
		UserName = userName;
		this.role = role;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public ArrayList<String> getLinkedAccounts() {
		return linkedAccounts;
	}

	public void setLinkedAccounts(ArrayList<String> linkedAccounts) {
		this.linkedAccounts = linkedAccounts;
	}

}
