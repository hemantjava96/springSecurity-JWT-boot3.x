package com.hk.sec.prep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hk.sec.prep.dto.LogInResponse;
import com.hk.sec.prep.dto.LoginRequest;
import com.hk.sec.prep.dto.RefreshTokenRequest;
import com.hk.sec.prep.dto.SignUpRequest;
import com.hk.sec.prep.entity.User;
import com.hk.sec.prep.services.AuthenticationService;

@RestController
@RequestMapping("/public/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/signUp")
	public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
		return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
		
	}
	
	@PostMapping("/logIn")
	public ResponseEntity<LogInResponse> signUp(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authenticationService.logIn(loginRequest));
		
	}
	
	@PostMapping("/refreshToken")
	public ResponseEntity<LogInResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
		
	}

}
