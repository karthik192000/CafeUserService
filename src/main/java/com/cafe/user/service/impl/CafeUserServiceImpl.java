package com.cafe.user.service.impl;

import com.cafe.user.beans.CreateUserRequest;
import com.cafe.user.beans.LoginRequest;
import com.cafe.user.beans.TokenResponse;
import com.cafe.user.beans.TokenValidationResponse;
import com.cafe.user.helper.Jwtutil;
import com.cafe.user.model.User;
import com.cafe.user.repository.UserDetailsRepository;
import com.cafe.user.service.CafeUserService;
import com.cafe.user.service.CustomUserDetailsService;
import com.cafe.user.util.ModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
public class CafeUserServiceImpl implements CafeUserService {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private Jwtutil jwtutil;

    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public String signUp(CreateUserRequest createUserRequest) {
        User user = ModelDto.toModel(createUserRequest);
        if(user != null){
            userDetailsRepository.save(user);
        }
        return "Sign-up successful";
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));
        }
        catch (UsernameNotFoundException ex){
            //
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUserName());
        return new TokenResponse(jwtutil.generateToken(userDetails));
    }


    @Override
    public TokenValidationResponse validateToken(HttpHeaders httpHeaders) {
        TokenValidationResponse tokenValidationResponse= new TokenValidationResponse();
        String status = "INVALID";
        User user = null;
        if(httpHeaders != null){
            List<String> headerList = httpHeaders.get("authtoken");
            if(!CollectionUtils.isEmpty(headerList)){
                String authToken = headerList.get(0);
                user = jwtutil.validateToken(authToken);
                if(user != null){
                    tokenValidationResponse.setUserName(user.getEmail());
                    tokenValidationResponse.setRole(user.getUserrole());
                    tokenValidationResponse.setStatus("VALID");
                }
            }
        }
        return  tokenValidationResponse;
    }
}

