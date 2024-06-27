package com.Authentication.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.UserLoginRequestDTO;

import com.Authentication.Application.service.UserLoginService;


import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;


@RequestMapping("/api/v1/")
@RestController
public class UserLoginController {


	private final UserLoginService userLoginService;
	
	
	@Autowired
	public UserLoginController(UserLoginService userLoginService) {
		super();
		this.userLoginService = userLoginService;
	}


	@PostMapping("login")
	public ResponseEntity<String> loginUser(@Valid @org.springframework.web.bind.annotation.RequestBody UserLoginRequestDTO userLoginRequestDTO) throws AuthException{
		System.out.println("Login DTO :"+userLoginRequestDTO.getPassword());
		String Response = userLoginService.loginUser(userLoginRequestDTO);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(Response);
	}
	
	
}
