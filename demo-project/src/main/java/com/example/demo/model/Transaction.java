package com.example.demo.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "Transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
//	@Column(name = "transId")
	private long transId;
	
	@Column(name = "amt")
	private long amt;
	
	@Column(name = "transType")
	private String transType;
	
	@Column(name = "date")
	private Date date;
	
	public Transaction() {
		
	}
	public Transaction(long transId, long amt, String transType, Date date) {
		super();
		this.transId = transId;
		this.amt = amt;
		this.transType = transType;
		this.date = date;
	}
	public long getTransId() {
		return transId;
	}
	public void setTransId(long transId) {
		this.transId = transId;
	}
	public long getAmt() {
		return amt;
	}
	public void setAmt(long amt) {
		this.amt = amt;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
}
