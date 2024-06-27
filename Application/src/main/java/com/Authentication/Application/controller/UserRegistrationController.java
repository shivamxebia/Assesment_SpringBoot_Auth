package com.Authentication.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.UserRegistrationRequestDTO;

import com.Authentication.Application.service.UserRegistrationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;


@RequestMapping("/api/v1/")
@RestController
public class UserRegistrationController {

	private final UserRegistrationService userRegistrationService;
	
	
	@Autowired
	public UserRegistrationController(UserRegistrationService userRegistrationService) {
		super();
		this.userRegistrationService = userRegistrationService;
	}
	
	@PostMapping("register")
	public ResponseEntity<String> registerUser(@Valid @org.springframework.web.bind.annotation.RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) throws AuthException{
		
		
		String Response = userRegistrationService.registerUser(userRegistrationRequestDTO);
//		System.out.println("register DTO :"+userRegistrationRequestDTO.getPassword());	
		return ResponseEntity.status(HttpStatus.CREATED).body(Response);
	}
	

	
	
}
