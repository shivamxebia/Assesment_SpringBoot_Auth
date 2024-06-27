package com.Authentication.Application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNotExistException(String message) {
        super(message);
    }
}
