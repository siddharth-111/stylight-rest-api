package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.LoginUserDetails;
import com.example.SpringBoot.service.serviceInterface.LoginUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailsServiceImpl implements LoginUserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new LoginUserDetails(s);
    }
}
