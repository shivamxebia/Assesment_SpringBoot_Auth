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

	// Injecting UserRegisterRepository for database operations
	private final UserRegisterRepository userRegisterRepository;

	@Autowired
	public UserServicesImpl(UserRegisterRepository userRegisterRepository) {
		this.userRegisterRepository = userRegisterRepository;

	}

	// Method to retrieve a paginated list of all users
	@Override
	public List<Users> getAllUser(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Users> userPage = userRegisterRepository.findAll(pageable);

		return userPage.getContent();
	}

	// Method to check if a password meets the required criteria
	private boolean isValidPassword(String password) {
		return password != null && password.length() >= 8 && password.matches(".*\\d.*") // It contains at least one
																							// digit
				&& password.matches(".*[!@#$%^&*()\\-_+=<>?].*"); // It contains at least one special character
	}

	// Method to reset user password
	@Override
	public void resetPassword(UserPasswordResetDTO userPasswordResetDTO) throws AuthException {
		Optional<Users> userOptional = userRegisterRepository.findByEmail(userPasswordResetDTO.getEmail());
		if (!userOptional.isPresent()) {
			throw new AuthException(HttpStatus.NOT_FOUND, "User not found.");
		}
		Users user = userOptional.get();
		// Check if OTP is expired ,ActiveOtpTime is checking
		// that OTP is still valid time (expiring time = 5 minutes)
		LocalDateTime ActiveOtpTime = user.getForgotPasswordOtpTimeAndDate().plusMinutes(5);
		if (!LocalDateTime.now().isBefore(ActiveOtpTime)) {
			throw new AuthException(HttpStatus.UNAUTHORIZED, "OTP Expired,Generate Again!!");
		}
		// throw exception if password mismatch
		if (!userPasswordResetDTO.getForgotPasswordOtp().equals(user.getForgotPasswordOtp())) {
			throw new AuthException(HttpStatus.BAD_REQUEST, "Incorrect OTP,Try Again!!");
		}
		// validate new entered password
		// using helper method "isValidPassword" which has following criteria
		// password length must be >=8 and consists upperCase , lowerCase and Special
		// characters
		String newPassword = userPasswordResetDTO.getNewPassword();
		if (!isValidPassword(newPassword)) {
			throw new AuthException(HttpStatus.BAD_REQUEST,
					"Invalid password format. Password should meet the criteria.");
		}
		// Reset OTP fields and update password
		user.setForgotPasswordOtp(null);
		user.setForgotPasswordOtpTimeAndDate(null);

		user.setPassword(newPassword);
		userRegisterRepository.save(user);
	}

	// method to generate random OTP of length 6
	private String generateOTP() {
		Random random = new Random();
		int otpLength = 6;
		StringBuilder otp = new StringBuilder();

		for (int i = 0; i < otpLength; i++) {
			otp.append(random.nextInt(10));
		}

		return otp.toString();
	}

	// Method to initiate forgot password process and return OTP
	@Override
	public String forgotPassword(String email) throws AuthException {
		Optional<Users> userOptional = userRegisterRepository.findByEmail(email);

		if (!userOptional.isPresent()) {
			throw new AuthException(HttpStatus.NOT_FOUND, "User not found.");
		}

		String OTP = generateOTP();
		Users user = userOptional.get();

		user.setForgotPasswordOtp(OTP);
		user.setForgotPasswordOtpTimeAndDate(LocalDateTime.now());
		userRegisterRepository.save(user);
		return OTP;

	}

	// Method to verify OTP during password reset [Created to just validate OTP]
	@Override
	public void verifyOTP(OneTimePasswordDTO oneTimePasswordDTO) throws AuthException {
		Optional<Users> userOptional = userRegisterRepository.findByEmail(oneTimePasswordDTO.getEmail());
		if (!userOptional.isPresent()) {
			throw new AuthException(HttpStatus.NOT_FOUND, "User not found.");
		}
		Users user = userOptional.get();
		LocalDateTime ActiveOtpTime = user.getForgotPasswordOtpTimeAndDate().plusMinutes(5);
		if (!LocalDateTime.now().isBefore(ActiveOtpTime)) {
			throw new AuthException(HttpStatus.UNAUTHORIZED, "OTP Expired,Generate Again!!");
		}
		if (!oneTimePasswordDTO.getOTP().equals(user.getForgotPasswordOtp())) {
			throw new AuthException(HttpStatus.BAD_REQUEST, "Incorrect OTP,Try Again!!");
		}
		//Reset fields 
		user.setForgotPasswordOtp(null);
		user.setForgotPasswordOtpTimeAndDate(null);

	}
}
