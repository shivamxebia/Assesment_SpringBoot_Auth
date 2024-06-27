package com.Authentication.Application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserNotExistException(String message,Throwable status) {
        super(message,status);
    }
}
