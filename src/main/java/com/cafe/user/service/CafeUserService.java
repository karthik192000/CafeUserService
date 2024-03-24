package com.cafe.user.service;

import com.cafe.user.beans.CreateUserRequest;
import com.cafe.user.beans.LoginRequest;
import com.cafe.user.beans.TokenResponse;
import com.cafe.user.beans.TokenValidationResponse;
import org.springframework.http.HttpHeaders;


public interface CafeUserService{


    public String signUp(CreateUserRequest createUserRequest);

    public TokenResponse login(LoginRequest loginRequest);

    public TokenValidationResponse validateToken(HttpHeaders httpHeaders);
}
