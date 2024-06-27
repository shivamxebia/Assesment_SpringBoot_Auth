package com.Authentication.Application.request;

public class UserPasswordResetDTO {

    private String email;
    private String forgotPasswordOtp;
    private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getForgotPasswordOtp() {
		return forgotPasswordOtp;
	}

	public void setForgotPasswordOtp(String forgotPasswordOtp) {
		this.forgotPasswordOtp = forgotPasswordOtp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}


