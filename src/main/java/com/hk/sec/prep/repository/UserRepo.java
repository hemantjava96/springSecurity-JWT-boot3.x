package com.hk.sec.prep.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hk.sec.prep.entity.Role;
import com.hk.sec.prep.entity.User;
import java.util.List;


public interface UserRepo extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);
	User findByRole(Role role);

}
