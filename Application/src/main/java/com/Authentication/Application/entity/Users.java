package com.Authentication.Application.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "users")
public class Users implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column
	private Long id;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column(unique = true)
	private String username;
	
	@Column(unique = true)
	private String email;
	
	@Column(unique = true)
	private String password;
	
	@Column
	private String phoneNumber;
	
	@Column
	private String address;
	
	@Column
	private Integer noOfAttempts;
	
	@Column
	private boolean isBlocked;
	
	@Column
	private LocalDateTime userCreatedTime = LocalDateTime.now();
	
	@Column
	private String forgotPasswordOtp;
	
	@Column
	private LocalDateTime forgotPasswordOtpTimeAndDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getNoOfAttempts() {
		return noOfAttempts;
	}

	public void setNoOfAttempts(Integer noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public LocalDateTime getUserCreatedTime() {
		return userCreatedTime;
	}

	public void setUserCreatedTime(LocalDateTime userCreatedTime) {
		this.userCreatedTime = userCreatedTime;
	}

	public String getForgotPasswordOtp() {
		return forgotPasswordOtp;
	}

	public void setForgotPasswordOtp(String forgotPasswordOtp) {
		this.forgotPasswordOtp = forgotPasswordOtp;
	}
	
	public LocalDateTime getForgotPasswordOtpTimeAndDate() {
		return forgotPasswordOtpTimeAndDate;
	}

	public void setForgotPasswordOtpTimeAndDate(LocalDateTime forgotPasswordOtpTimeAndDate) {
		this.forgotPasswordOtpTimeAndDate = forgotPasswordOtpTimeAndDate;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + ", address=" + address + ", noOfAttempts="
				+ noOfAttempts + ", isBlocked=" + isBlocked + ", userCreatedTime=" + userCreatedTime
				+ ", forgotPasswordOtpTimeAndDate=" + forgotPasswordOtpTimeAndDate + "]";
	}
	

	

	
	
	

	
}
