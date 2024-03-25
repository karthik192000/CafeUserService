package com.cafe.user.util;

import com.cafe.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            if (authentication != null) {
                String userName = String.valueOf(authentication.getPrincipal());
                String password = String.valueOf(authentication.getCredentials());
                Collection<?> authorities = authentication.getAuthorities();

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
                if (userDetails == null) {
                    throw new BadCredentialsException("User details not found");
                }
                if (!userDetails.getUsername().equals(userName)) {
                    throw new BadCredentialsException("Invalid username");
                }
                if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                    throw new BadCredentialsException("Invalid password");
                }
                if (CollectionUtils.isEmpty(authorities)) {
                    throw new BadCredentialsException("Please select a role");
                }

                SimpleGrantedAuthority authority = (SimpleGrantedAuthority) authorities.stream().findFirst().orElse(null);
                if (!userDetails.getAuthorities().contains(authority)) {
                    throw new BadCredentialsException("Invalid role");
                }
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities().stream().collect(Collectors.toList()));
                return usernamePasswordAuthenticationToken;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }
}
