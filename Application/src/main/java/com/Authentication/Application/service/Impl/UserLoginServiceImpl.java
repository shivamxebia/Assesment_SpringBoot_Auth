package com.Authentication.Application.service.Impl;

import java.time.LocalDateTime;
import java.util.Optional;

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
	
	// Injecting UserRegisterRepository for database operations
	private final UserRegisterRepository userRegisterRepository ;

	
	@Autowired
	public UserLoginServiceImpl(UserRegisterRepository userRegisterRepository) {
		this.userRegisterRepository = userRegisterRepository;	
		
	}
	
	
	@Override
	public String loginUser(UserLoginRequestDTO userLoginRequestDTO) throws AuthException {

		// Check if the user exists based on username
		Optional<Users> optionalUser = userRegisterRepository.findByUsername(userLoginRequestDTO.getUsername());
 
		// If User does not exist
		if (!optionalUser.isPresent()) {
			throw new AuthException(HttpStatus.NOT_FOUND,"Invalid Credentials,Try Again!!");
		}
		// Retrieve the user object
		Users user = optionalUser.get();

		// Check if the password matches
		if (userLoginRequestDTO.getPassword().equals(user.getPassword())) {
			// Check if the user is blocked
			if (user != null && user.getIsBlocked()) {
				// Calculate when the block ends (24 hours from the blocked time)
			    LocalDateTime blockEndTime = user.getBlockedTime().plusHours(24);
			    if (LocalDateTime.now().isBefore(blockEndTime)) {
			        throw new AuthException(HttpStatus.UNAUTHORIZED, "Account is locked. Try again after 24 hours.");
			    } else {
			    	// Unblock the account if the block period has passed
			        user.setIsBlocked(false);
			        user.setNoOfAttempts(0);
			        user.setBlockedTime(null); 
			        userRegisterRepository.save(user);
			        return "Login Successfully";
			    }
			}else {
				// Reset login attempt count and time since last attempt
				user.setNoOfAttempts(0);
				user.setLastLoginAttemptTime(null);
				userRegisterRepository.save(user);
				return "Logged in Successfully";
			}

		} else {
 
			// Handling Incorrect Password Attempts and Blocking User

            // Increment login attempts and record the time of the attempt
			user.setNoOfAttempts(user.getNoOfAttempts() + 1);
			user.setLastLoginAttemptTime(LocalDateTime.now());
			// If attempts exceed 5, block the account
			if (user.getNoOfAttempts() >= 5) {
				user.setIsBlocked(true);
				user.setBlockedTime(LocalDateTime.now());
				userRegisterRepository.save(user);
				throw new AuthException(HttpStatus.UNAUTHORIZED,"Account locked For 24hrs, due to 5 failed login attempts.");
			}
			// Save user data after login attempt
			userRegisterRepository.save(user);
			// Throw exception for invalid username or password
			throw new AuthException(HttpStatus.UNAUTHORIZED,"Invalid username or password. Attempt " + user.getNoOfAttempts() + " of 5.");
		}
 
 

 
		
	}
	
	
	
	
}