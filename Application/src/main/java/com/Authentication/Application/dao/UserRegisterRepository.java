package com.Authentication.Application.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Authentication.Application.entity.Users;
@Repository
public interface UserRegisterRepository extends JpaRepository<Users, Long>{
	
	// Method to find a user by username
	Optional<Users> findByUsername(String username);
	
	// Method to find a user by email
	Optional<Users> findByEmail(String email);

	

}
