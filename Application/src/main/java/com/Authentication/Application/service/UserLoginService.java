package com.Authentication.Application.service;

import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.UserLoginRequestDTO;

public interface UserLoginService {

	String loginUser(UserLoginRequestDTO userLoginRequestDTO) throws AuthException;

}
