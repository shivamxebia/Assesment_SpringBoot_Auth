package com.Authentication.Application.service;

import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.UserPasswordResetDTO;

public interface UserPasswordResetService {
	void resetPassword(UserPasswordResetDTO userPasswordResetDTO) throws AuthException;
}
