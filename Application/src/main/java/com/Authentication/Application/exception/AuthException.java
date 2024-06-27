package com.Authentication.Application.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9039412236725676320L;
	private HttpStatus httpStatus;

	public AuthException() {
		super();
	}

	public AuthException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}




