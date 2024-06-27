package com.Authentication.Application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Authentication.Application.entity.Users;
import com.Authentication.Application.service.UserServices;

@RequestMapping("/api/v1/")
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
	
}
