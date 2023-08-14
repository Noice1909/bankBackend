package com.example.demo.model;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "dob", nullable=false)
	private Date dob;
	
	@Column(name = "password", nullable = false)
	private String pwd;
	
	@Column(name = "name", nullable=false)
	private String name;
	
	@Column(name = "mobile_no", length=10, nullable=false)
	private long mobileNo;
	
	@Column(name  = "address", length=100, nullable=false)
	private String address;
	

	@Column(name = "email", nullable=false)
	private String email;
	
	public Customer() {
		
	}

	public Customer(long userId, Date dob, String pwd, String name, long mobileNo, String address, String email) {
		super();
		this.userId = userId;
		this.dob = dob;
		this.pwd = pwd;
		this.name = name;
		this.mobileNo = mobileNo;
		this.address = address;
		this.email = email;
	}

	public long getUserId() {
		return userId;
	}

	public Date getDob() {
		return dob;
	}

	public String getPwd() {
		return pwd;
	}

	public String getName() {
		return name;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
