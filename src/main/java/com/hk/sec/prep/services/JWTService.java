package com.hk.sec.prep.services;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
	public String generateToken(UserDetails userDetails);
	public String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails);
	public String extractUserName(String token);
	public boolean isTokenValid(String token, UserDetails userDetails);

}
