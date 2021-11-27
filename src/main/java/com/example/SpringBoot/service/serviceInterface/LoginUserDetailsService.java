package com.example.SpringBoot.service.serviceInterface;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface LoginUserDetailsService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
