package com.cafe.user.service.impl;

import com.cafe.user.beans.*;
import com.cafe.user.helper.Jwtutil;
import com.cafe.user.model.CustomerDetails;
import com.cafe.user.model.User;
import com.cafe.user.repository.CustomerDetailsRepository;
import com.cafe.user.repository.UserDetailsRepository;
import com.cafe.user.service.CafeUserService;
import com.cafe.user.service.CustomUserDetailsService;
import com.cafe.user.util.CafeUserServiceException;
import com.cafe.user.util.ModelDto;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class CafeUserServiceImpl implements CafeUserService {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private Jwtutil jwtutil;

    @Autowired
    CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public SignUpResponse signUp(CreateUserRequest createUserRequest){
        User user = ModelDto.toModel(createUserRequest);
        User savedUser = userDetailsRepository.findByEmail(user.getEmail());
        SignUpResponse signUpResponse = new SignUpResponse();

        try {
            if (savedUser != null) {
                throw new CafeUserServiceException("Username not available",HttpStatus.NOT_ACCEPTABLE);
            }
            savedUser = userDetailsRepository.save(user);
            signUpResponse.setName(savedUser.getName());
            signUpResponse.setUserName(savedUser.getEmail());
            signUpResponse.setUserRole(savedUser.getUserrole());
        }
        catch (CafeUserServiceException ex){
            throw ex;
        }
        catch (Exception ex){
            throw new CafeUserServiceException("Exception occurred while saving user details.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return signUpResponse;
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        TokenResponse tokenResponse = null;
        try{
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + loginRequest.getRole());
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(authority);
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword(),grantedAuthorities));


        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUserName());
        Set<SimpleGrantedAuthority> authoritySet = (Set<SimpleGrantedAuthority>)userDetails.getAuthorities();
        SimpleGrantedAuthority savedAuthority =authoritySet.stream().findFirst().orElse(null);
        String role = savedAuthority.getAuthority().split("_")[1];
        String authtoken = jwtutil.generateToken(userDetails);
        tokenResponse =    new TokenResponse(userDetails.getUsername(),role,authtoken);
        }
        catch (BadCredentialsException ex){
            throw new CafeUserServiceException("Invalid Credentials", HttpStatus.UNAUTHORIZED);
        }
        catch (Exception ex){
            throw new CafeUserServiceException("Exception occurred while loggin in user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return tokenResponse;
    }


    @Override
    public TokenValidationResponse validateToken(HttpHeaders httpHeaders) {
        TokenValidationResponse tokenValidationResponse= new TokenValidationResponse();
        try {
            String status = "INVALID";
            User user = null;
            if (httpHeaders != null) {
                List<String> headerList = httpHeaders.get("authtoken");
                if (!CollectionUtils.isEmpty(headerList)) {
                    String authToken = headerList.get(0);
                    user = jwtutil.validateToken(authToken);
                    if (user != null) {
                        tokenValidationResponse.setUserName(user.getEmail());
                        tokenValidationResponse.setRole(user.getUserrole());
                        tokenValidationResponse.setStatus("VALID");
                    }
                }
            }
        }
        catch (ExpiredJwtException ex){
            tokenValidationResponse.setStatus("EXPIRED");
        }
        catch (Exception ex){
            throw new CafeUserServiceException("Exception occurred during token validation",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  tokenValidationResponse;
    }


    private void createCustomer(User user){
        String userRole = user.getUserrole();
        if(userRole.equals("ROLE_CUSTOMER")){
            CustomerDetails customerDetails = new CustomerDetails();
            customerDetails.setUserid(user.getUserid());
            customerDetails.setCustomersince((Date) Date.from(Instant.now()));
        }
    }
}

