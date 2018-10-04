package com.revature.BankingApp.models;

import java.util.UUID;

public class Account {

	public String accountId= UUID.randomUUID().toString();
	public float balance=0;
	public  boolean open =true ;
}