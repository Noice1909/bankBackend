package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "Account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "acc_no")
	private long accNo;
	
	@ManyToOne
	@JoinColumn(name = "user_Id")
	private Customer customer;
	
	@Column (name = "acc_type")
	private String accType;
	
	@Column (name = "balance")
	private double balance=0.0;

	public Account() {
		
	}

	public Account(long accNo, Customer customer, String accType, double balance) {
		super();
		this.accNo = accNo;
		this.customer = customer;
		this.accType = accType;
		this.balance = balance;
	}

	public long getAccNo() {
		return accNo;
	}

	public Customer getCustomer() {
		return customer;
	}

	public String getAccType() {
		return accType;
	}

	public double getBalance() {
		return balance;
	}

	public void setAccNo(long accNo) {
		this.accNo = accNo;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	
	
	
}
