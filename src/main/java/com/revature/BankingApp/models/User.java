package com.revature.BankingApp.models;

import java.util.UUID;

public class User{
	public String userId= UUID.randomUUID().toString();
	public String userName;
	public String password;
	public String role;

}
