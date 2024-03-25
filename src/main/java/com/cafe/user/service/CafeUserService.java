package com.cafe.user.service;

import com.cafe.user.beans.*;
import org.springframework.http.HttpHeaders;


public interface CafeUserService{


    public SignUpResponse signUp(CreateUserRequest createUserRequest);

    public TokenResponse login(LoginRequest loginRequest);

    public TokenValidationResponse validateToken(HttpHeaders httpHeaders);
}
