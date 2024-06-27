package com.Authentication.Application.service;

import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.UserRegistrationRequestDTO;

public interface UserRegistrationService {

	String registerUser(UserRegistrationRequestDTO userRegistrationRequestDTO) throws AuthException;
	
	boolean isUsernameUnique(String username);
	
	boolean isEmailUnique(String email);

	


	
}
