package com.Authentication.Application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {

	@ExceptionHandler(value = { AuthException.class })
	public ResponseEntity<Object> handleAuthException(AuthException authException) {
		return ResponseEntity.status(authException.getHttpStatus()).body(authException.getMessage());

	}

	@ExceptionHandler(value = { RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException runtimeException) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!! please try again later");

	}
	
    
}
