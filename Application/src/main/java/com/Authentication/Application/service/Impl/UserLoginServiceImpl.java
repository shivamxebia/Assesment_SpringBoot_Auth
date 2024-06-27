package com.Authentication.Application.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.Authentication.Application.dao.UserRegisterRepository;
import com.Authentication.Application.entity.Users;
import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.UserLoginRequestDTO;
import com.Authentication.Application.service.UserLoginService;


@Service
public class UserLoginServiceImpl implements UserLoginService {
	
	private final UserRegisterRepository userRegisterRepository ;

	
	@Autowired
	public UserLoginServiceImpl(UserRegisterRepository userRegisterRepository) {
		this.userRegisterRepository = userRegisterRepository;	
		
	}
	
	@Override
    public String loginUser(UserLoginRequestDTO userLoginRequestDTO) throws AuthException {

		Users user = userRegisterRepository.findByUsernameAndPassword(userLoginRequestDTO.getUsername(),userLoginRequestDTO.getPassword());

		if (user != null && user.getpassword().equals(userLoginRequestDTO.getPassword())) {
            return "Login successful!";
        } else {
           throw new AuthException(HttpStatus.BAD_REQUEST, "Invalid credentials. Please try again.");
        }
       
    }
}