package com.cafe.user.service;


import com.cafe.user.beans.CafeUserDetails;
import com.cafe.user.model.User;
import com.cafe.user.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User userDetails = userDetailsRepository.findByEmail(userName);

        if(userDetails == null){
            throw new UsernameNotFoundException("Invalid Username provided");
        }

        return new CafeUserDetails(userDetails);
    }
}
