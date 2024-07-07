package com.hk.sec.prep.services;

import com.hk.sec.prep.dto.LogInResponse;
import com.hk.sec.prep.dto.LoginRequest;
import com.hk.sec.prep.dto.RefreshTokenRequest;
import com.hk.sec.prep.dto.SignUpRequest;
import com.hk.sec.prep.entity.User;

public interface AuthenticationService {
	public User signUp(SignUpRequest signUpRequest);
	public LogInResponse logIn(LoginRequest loginRequest);
	public LogInResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
