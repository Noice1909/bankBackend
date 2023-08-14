package com.example.demo.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "Transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "trans_id")
	private long transId;
	
	@ManyToOne
	@JoinColumn(name = "acc_no")
	private Account account;
	
	@Column(name = "amt")
	private double amt;
	
	@Column(name = "transType")
	private String transType;
	
	@Column(name = "date")
	private Date date;
	
	public Transaction() {
		
	}

	public Transaction(long transId, Account account, double amt, String transType, Date date) {
		super();
		this.transId = transId;
		this.account = account;
		this.amt = amt;
		this.transType = transType;
		this.date = date;
	}

	public long getTransId() {
		return transId;
	}

	public Account getAccount() {
		return account;
	}

	public double getAmt() {
		return amt;
	}

	public String getTransType() {
		return transType;
	}

	public Date getDate() {
		return date;
	}

	public void setTransId(long transId) {
		this.transId = transId;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
}
