package com.Authentication.Application.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Authentication.Application.entity.Users;
@Repository
public interface UserRegisterRepository extends JpaRepository<Users, Long>{

	Optional<Users> findByUsername(String username);
	Optional<Users> findByEmail(String email);
	Users findByUsernameAndPhoneNumber(String username,String phoneNumber);
	Users findByUsernameAndPassword(String username,String password);
	

}
