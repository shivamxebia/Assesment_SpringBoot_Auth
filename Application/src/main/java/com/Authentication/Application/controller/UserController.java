package com.Authentication.Application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Authentication.Application.entity.Users;
import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.OneTimePasswordDTO;
import com.Authentication.Application.request.UserPasswordResetDTO;
import com.Authentication.Application.service.UserServices;

@RequestMapping("/api/v1")
@RestController
public class UserController {
	private final UserServices userService;
	@Autowired
	public UserController(UserServices userService) {
		super();
		this.userService = userService;
	}
	@GetMapping
	public ResponseEntity<List<Users>> getAllUser(
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		List<Users> userList = userService.getAllUser(pageNumber, pageSize);
		return ResponseEntity.status(HttpStatus.OK).body(userList);
	}
	
    @PatchMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody UserPasswordResetDTO userPasswordResetDTO) throws AuthException {

    	userService.resetPassword(userPasswordResetDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Password reset successful!");

    }
    
    @GetMapping("/forgot-password/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email) throws AuthException {
    	String response = userService.forgotPassword(email);
        return ResponseEntity.status(HttpStatus.OK).body("Your OTP For Reset Password "+ response);

    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestBody OneTimePasswordDTO oneTimePasswordDTO) throws AuthException {

    	userService.verifyOTP(oneTimePasswordDTO);
        return ResponseEntity.status(HttpStatus.OK).body("OTP Verified!!");

    } 
	
}
