package com.Authentication.Application.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Authentication.Application.dao.UserRegisterRepository;
import com.Authentication.Application.entity.Users;
import com.Authentication.Application.service.UserServices;


@Service
public class UserServicesImpl implements UserServices {

	private final UserRegisterRepository userRegisterRepository ;
	
	@Autowired
	public UserServicesImpl(UserRegisterRepository userRegisterRepository) {
		this.userRegisterRepository = userRegisterRepository;	
		
	}

	@Override
	public List<Users> getAllUser(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Users> userPage = userRegisterRepository.findAll(pageable);

		return userPage.getContent();
	}
}
