package com.example.SpringBoot.service.serviceInterface;

import com.example.SpringBoot.Model.LoginUserDetails;
import org.apache.juli.logging.Log;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface LoginUserDetailsService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
    LoginUserDetails createLoginUser(LoginUserDetails loginUserDetails) throws Exception;
}
