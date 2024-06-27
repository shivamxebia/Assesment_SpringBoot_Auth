package com.Authentication.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.UserPasswordResetDTO;
import com.Authentication.Application.service.UserPasswordResetService;


@RestController
@RequestMapping("/api/v1/")
public class UserPasswordResetController {

    private final UserPasswordResetService userPasswordResetService;

    @Autowired
    public UserPasswordResetController(UserPasswordResetService userPasswordResetService) {
        this.userPasswordResetService = userPasswordResetService;
    }

    @PatchMapping("reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody UserPasswordResetDTO userPasswordResetDTO) throws AuthException {

        	userPasswordResetService.resetPassword(userPasswordResetDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Password reset successful!");

    }
}
