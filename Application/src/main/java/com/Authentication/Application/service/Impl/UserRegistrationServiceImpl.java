package com.Authentication.Application.service.Impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.Authentication.Application.dao.UserRegisterRepository;
import com.Authentication.Application.entity.Users;
import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.UserRegistrationRequestDTO;
import com.Authentication.Application.service.UserRegistrationService;
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService{

	private final UserRegisterRepository userRegisterRepository ;
	private final ModelMapper modelMapper;
	
	@Autowired
	public UserRegistrationServiceImpl(UserRegisterRepository userRegisterRepository,ModelMapper modelMapper) {
		this.userRegisterRepository = userRegisterRepository;
		this.modelMapper = modelMapper;
		
	}
	
	
	
	
	   @Override
	    public boolean isUsernameUnique(String username) {
	        Optional<Users> user = userRegisterRepository.findByUsername(username);
	        return !user.isPresent();
	    }

	    @Override
	    public boolean isEmailUnique(String email) {
	        Optional<Users> user = userRegisterRepository.findByEmail(email);
	        return !user.isPresent(); 
	    }


	@Override 
	public String registerUser(UserRegistrationRequestDTO userRegistrationRequestDTO) throws AuthException {
		
		Users user = modelMapper.map(userRegistrationRequestDTO, Users.class);
System.out.println("Register : "+userRegistrationRequestDTO.getPassword());
		if(!isUsernameUnique(user.getUsername())) {
			throw new AuthException(HttpStatus.BAD_REQUEST,"Username already exists. Please choose a different username.");
		}
		if(!isEmailUnique(user.getEmail())) {
			 throw new AuthException(HttpStatus.BAD_REQUEST,"Email address already exists, Try with different email");
		}
		userRegisterRepository.save(user);
		return "User Created Successfully!!";
	}
}
