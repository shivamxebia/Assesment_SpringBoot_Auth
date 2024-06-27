package com.Authentication.Application.service;

import java.util.List;

import com.Authentication.Application.entity.Users;

public interface UserServices {

	public List<Users> getAllUser(Integer pageNumber, Integer pageSize) ;

}
