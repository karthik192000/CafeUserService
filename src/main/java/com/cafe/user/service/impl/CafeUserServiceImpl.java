package com.cafe.user.service.impl;

import com.cafe.user.beans.*;
import com.cafe.user.helper.Jwtutil;
import com.cafe.user.model.CustomerDetails;
import com.cafe.user.model.User;
import com.cafe.user.repository.CustomerDetailsRepository;
import com.cafe.user.repository.UserDetailsRepository;
import com.cafe.user.service.CafeUserService;
import com.cafe.user.service.CustomUserDetailsService;
import com.cafe.user.util.ModelDto;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
                throw new IllegalArgumentException("User name not available");
            }
            savedUser = userDetailsRepository.save(user);
            signUpResponse.setName(savedUser.getName());
            signUpResponse.setUserName(savedUser.getEmail());
            signUpResponse.setUserRole(savedUser.getUserrole());
        }
        catch (Exception ex){
            throw ex;
        }
        return signUpResponse;
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        try{
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + loginRequest.getRole());
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(authority);
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword(),grantedAuthorities));
        }
        catch (BadCredentialsException ex){
            throw ex;
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUserName());
        Set<SimpleGrantedAuthority> authoritySet = (Set<SimpleGrantedAuthority>)userDetails.getAuthorities();
        SimpleGrantedAuthority authority =authoritySet.stream().findFirst().orElse(null);
        String role = authority.getAuthority().split("_")[1];
        String authtoken = jwtutil.generateToken(userDetails);
        return new TokenResponse(userDetails.getUsername(),role,authtoken);
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
            ex.printStackTrace();
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

