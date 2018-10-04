package com.revature.BankingApp.models;

import java.io.Serializable;
import java.util.UUID;

public class Account implements Serializable {

	private static final long serialVersionUID = 791907848829295773L;
	public String accountId= UUID.randomUUID().toString();
	public float balance=0;
	public  boolean open =true ;
}