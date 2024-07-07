package com.hk.sec.prep.services.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hk.sec.prep.dto.LogInResponse;
import com.hk.sec.prep.dto.LoginRequest;
import com.hk.sec.prep.dto.RefreshTokenRequest;
import com.hk.sec.prep.dto.SignUpRequest;
import com.hk.sec.prep.entity.Role;
import com.hk.sec.prep.entity.User;
import com.hk.sec.prep.repository.UserRepo;
import com.hk.sec.prep.services.AuthenticationService;
import com.hk.sec.prep.services.JWTService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired	
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;
	
	
	public User signUp(SignUpRequest signUpRequest) {
		User user =  new User();
		user.setFirstName(signUpRequest.firstName());
		user.setEmail(signUpRequest.email());
		user.setLastName(signUpRequest.lastName());
		System.out.println(signUpRequest.email() + " "+ signUpRequest.password());
		user.setPassword(passwordEncoder.encode(signUpRequest.password()));
		user.setRole(Role.USER);
		
		return userRepo.save(user);
	}
	
	public LogInResponse logIn(LoginRequest loginRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(),
				loginRequest.password()));
		User user = userRepo.findByEmail(loginRequest.email())
				.orElseThrow(()-> new IllegalArgumentException("Invalid Email"));
		String token = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
		
		return new LogInResponse(token, refreshToken);
	}
	
	
	public LogInResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
	
		String email = jwtService.extractUserName(refreshTokenRequest.refreshToken());
		System.out.println("AuthenticationServiceImpl.refreshToken()" + email);
		
		User user = userRepo.findByEmail(email).get();
		
		if(jwtService.isTokenValid(refreshTokenRequest.refreshToken(), user));
		String token = jwtService.generateToken(user);
		
		return new LogInResponse(token, refreshTokenRequest.refreshToken()); 
	} 

}
