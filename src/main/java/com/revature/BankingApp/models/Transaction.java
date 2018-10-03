package com.revature.BankingApp.models;

import java.util.Date;
import java.util.UUID;

public class Transaction {
	public String transactionID= UUID.randomUUID().toString();
	public String userId;
	public String accountId;
	public boolean reviewed = false;
	public boolean approval;
	public String action;
	public float amount;
	public Date date= new Date();
}
