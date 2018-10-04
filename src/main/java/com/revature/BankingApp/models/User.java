package com.revature.BankingApp.models;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 791907848829295773L;
	public String userId= UUID.randomUUID().toString();
	public String userName;
	public String password;
	public String role;

}
