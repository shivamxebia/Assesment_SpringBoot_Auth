package com.Authentication.Application.service.Impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.Authentication.Application.dao.UserRegisterRepository;
import com.Authentication.Application.entity.Users;
import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.UserPasswordResetDTO;
import com.Authentication.Application.request.UserRegistrationRequestDTO;
import com.Authentication.Application.service.UserRegistrationService;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	private final UserRegisterRepository userRegisterRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public UserRegistrationServiceImpl(UserRegisterRepository userRegisterRepository, ModelMapper modelMapper) {
		this.userRegisterRepository = userRegisterRepository;
		this.modelMapper = modelMapper;

	}

	// Function to check User with given username is present in database
	@Override
	public boolean isUsernameUnique(String username) {
		Optional<Users> user = userRegisterRepository.findByUsername(username);
		return !user.isPresent(); // user.isPresent() gives true then user exists already
	}

	@Override
	public boolean isEmailUnique(String email) {
		Optional<Users> user = userRegisterRepository.findByEmail(email);
		return !user.isPresent(); // user.isPresent() gives true then user exists already
	}

	@Override
	public String registerUser(UserRegistrationRequestDTO userRegistrationRequestDTO) throws AuthException {

		// mapping data using modelMapper
		Users user = modelMapper.map(userRegistrationRequestDTO, Users.class);

		// checking that if user with given details(username and email) exists or not
		// if any of following condition becomes true then throw AuthException with
		// "status code" and "message"
		if (!isUsernameUnique(user.getUsername())) {
			throw new AuthException(HttpStatus.BAD_REQUEST,
					"Username already exists. Please choose a different username.");
		}
		if (!isEmailUnique(user.getEmail())) {
			throw new AuthException(HttpStatus.BAD_REQUEST, "Email address already exists, Try with different email");
		}
		// saving user in the Repository (userRegisterRepository)
		userRegisterRepository.save(user);
		return "User Created Successfully!!";
	}

}
