package com.Authentication.Application.service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.Authentication.Application.dao.UserRegisterRepository;
import com.Authentication.Application.entity.Users;
import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.OneTimePasswordDTO;
import com.Authentication.Application.request.UserPasswordResetDTO;
import com.Authentication.Application.service.UserServices;


@Service
public class UserServicesImpl implements UserServices {

	private final UserRegisterRepository userRegisterRepository ;
	
	@Autowired
	public UserServicesImpl(UserRegisterRepository userRegisterRepository) {
		this.userRegisterRepository = userRegisterRepository;	
		
	}

	@Override
	public List<Users> getAllUser(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Users> userPage = userRegisterRepository.findAll(pageable);

		return userPage.getContent();
	}
	
	private boolean isValidPassword(String password) {
	    return password != null && password.length() >= 8
	            && password.matches(".*\\d.*") // It contains at least one digit
	            && password.matches(".*[!@#$%^&*()\\-_+=<>?].*"); // It contains at least one special character
	}
    @Override
    public void resetPassword(UserPasswordResetDTO userPasswordResetDTO) throws AuthException {
    	Optional<Users> userOptional = userRegisterRepository.findByEmail(userPasswordResetDTO.getEmail());
    	if(!userOptional.isPresent()) {
    		throw new AuthException(HttpStatus.NOT_FOUND, "User not found.");
    	}
    	Users user = userOptional.get();
	    LocalDateTime ActiveOtpTime = user.getForgotPasswordOtpTimeAndDate().plusMinutes(5);
	    if (LocalDateTime.now().isAfter(ActiveOtpTime)) {
	        throw new AuthException(HttpStatus.UNAUTHORIZED, "OTP Expired,Generate Again!!");
	    }   
	    if(!userPasswordResetDTO.getForgotPasswordOtp().equals(user.getForgotPasswordOtp())) {
	    	throw new AuthException(HttpStatus.BAD_REQUEST,"Incorrect OTP,Try Again!!");
	    }
	    String newPassword = userPasswordResetDTO.getNewPassword();
	    if (!isValidPassword(newPassword)) {
	        throw new AuthException(HttpStatus.BAD_REQUEST, "Invalid password format. Password should meet the criteria.");
	    }
    	user.setForgotPasswordOtp(null);
    	user.setForgotPasswordOtpTimeAndDate(null);

        user.setPassword(newPassword);
        userRegisterRepository.save(user);
    }
    
    private String generateOTP() {
        Random random = new Random();
        int otpLength = 6;
        StringBuilder otp = new StringBuilder();
        
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10)); 
        }
        
        return otp.toString();
    }
    
    @Override
    public String forgotPassword(String email) throws AuthException {
    	Optional<Users> userOptional = userRegisterRepository.findByEmail(email);
    	
    	if(!userOptional.isPresent()) {
    		throw new AuthException(HttpStatus.NOT_FOUND, "User not found.");
    	}
    	
    	String OTP = generateOTP();
    	Users user = userOptional.get();
    	
    	user.setForgotPasswordOtp(OTP);
    	user.setForgotPasswordOtpTimeAndDate(LocalDateTime.now());
    	userRegisterRepository.save(user);
    	return OTP;
    		
    }
    
    @Override
    public void verifyOTP(OneTimePasswordDTO oneTimePasswordDTO) throws AuthException{
    	Optional<Users> userOptional = userRegisterRepository.findByEmail(oneTimePasswordDTO.getEmail());
    	if(!userOptional.isPresent()) {
    		throw new AuthException(HttpStatus.NOT_FOUND, "User not found.");
    	}
    	Users user = userOptional.get();
	    LocalDateTime ActiveOtpTime = user.getForgotPasswordOtpTimeAndDate().plusMinutes(5);
	    if (LocalDateTime.now().isBefore(ActiveOtpTime)) {
	        throw new AuthException(HttpStatus.UNAUTHORIZED, "OTP Expired,Generate Again!!");
	    } 
	    if(!oneTimePasswordDTO.getOTP().equals(user.getForgotPasswordOtp())) {
	    	throw new AuthException(HttpStatus.BAD_REQUEST,"Incorrect OTP,Try Again!!");
	    }
    	user.setForgotPasswordOtp(null);
    	user.setForgotPasswordOtpTimeAndDate(null);

    
    }
}
