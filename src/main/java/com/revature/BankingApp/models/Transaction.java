package com.revature.BankingApp.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Transaction implements Serializable {

	private static final long serialVersionUID = 791907848829295773L;
	public String transactionID= UUID.randomUUID().toString();
	public String userId;
	public String accountId;
	public boolean reviewed = false;
	public boolean approval;
	public String action;
	public float amount;
	public Date date= new Date();
}
