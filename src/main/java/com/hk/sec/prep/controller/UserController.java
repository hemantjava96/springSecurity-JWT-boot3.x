package com.hk.sec.prep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hk.sec.prep.services.JWTService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/private/user")
public class UserController {

	@Autowired
	private JWTService jwtService;

	@GetMapping
	public ResponseEntity<String> user(HttpServletRequest request) {
		// Extract the Authorization header from the request
		String authHeader = request.getHeader("Authorization");

		// Check if the Authorization header starts with "Bearer "
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			String username = jwtService.extractUserName(token);
			return ResponseEntity.ok("User: " + username);
		} else {
			return ResponseEntity.badRequest().body("Invalid Authorization header");
		}
	}

	@GetMapping("/getEmail")
	public ResponseEntity<String> getEmail(@RequestHeader("Authorization") String authHeader) {

		// Check if the Authorization header starts with "Bearer "
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			String username = jwtService.extractUserName(token);
			return ResponseEntity.ok("User: " + username);
		} else {
			return ResponseEntity.badRequest().body("Invalid Authorization header");
		}
	}

}
