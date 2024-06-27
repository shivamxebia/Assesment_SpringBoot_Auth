package com.Authentication.Application.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.Authentication.Application.dao.UserRegisterRepository;
import com.Authentication.Application.entity.Users;
import com.Authentication.Application.exception.AuthException;
import com.Authentication.Application.request.UserPasswordResetDTO;
import com.Authentication.Application.service.UserPasswordResetService;

@Service
public class UserPasswordResetServiceImpl implements UserPasswordResetService {

    private final UserRegisterRepository userRegisterRepository;

    @Autowired
    public UserPasswordResetServiceImpl(UserRegisterRepository userRegisterRepository) {
        this.userRegisterRepository = userRegisterRepository;
    }

    @Override
    public void resetPassword(UserPasswordResetDTO userPasswordResetDTO) throws AuthException {
        Users user = userRegisterRepository.findByUsernameAndPhoneNumber(userPasswordResetDTO.getUsername(),userPasswordResetDTO.getPhoneNumber());

        if (user==null) {
            throw new AuthException(HttpStatus.NOT_FOUND, "User not found.");
        }

        user.setpassword(userPasswordResetDTO.getNewPassword());
        userRegisterRepository.save(user);
    }
}

