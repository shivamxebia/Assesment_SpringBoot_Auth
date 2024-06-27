package com.Authentication.Application.request;

public class OneTimePasswordDTO {
	
	private String email;
	private String OTP;

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
