package com.revature.BankingApp.models;

import java.util.Date;
import java.util.UUID;

public class Transaction {
	private String transactionID;
	private String userId;
	private String accountId;
	private boolean reviewed;
	private boolean approval;
	private String action;
	private float amount;
	private Date date;
	public Transaction(String userId, String accountId, boolean approval, String action, float amount) {
		super();
		this.transactionID = UUID.randomUUID().toString();
		this.userId = userId;
		this.accountId = accountId;
		this.approval = approval;
		this.action = action;
		this.amount = amount;
		this.date = new Date();
		this.reviewed = false;
	}
	public String getTransactionID() {
		return transactionID;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public boolean isReviewed() {
		return reviewed;
	}
	public void setReviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}
	public boolean isApproval() {
		return approval;
	}
	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	
	
}
