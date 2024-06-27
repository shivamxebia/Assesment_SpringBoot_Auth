package com.Authentication.Application.service;

import java.util.List;

import com.Authentication.Application.entity.Users;
import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.OneTimePasswordDTO;
import com.Authentication.Application.request.UserPasswordResetDTO;


public interface UserServices {

	public List<Users> getAllUser(Integer pageNumber, Integer pageSize) ;
	void resetPassword(UserPasswordResetDTO userPasswordResetDTO) throws AuthException;
	public String forgotPassword(String email) throws AuthException;
	public void verifyOTP(OneTimePasswordDTO oneTimePasswordDTO) throws AuthException;
	


}
