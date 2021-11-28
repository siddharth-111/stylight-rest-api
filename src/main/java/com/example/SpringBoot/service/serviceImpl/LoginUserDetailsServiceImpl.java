package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.LoginUserDetails;
import com.example.SpringBoot.dao.LoginUserDetailsDAO;
import com.example.SpringBoot.repository.LoginUserDetailsRepository;
import com.example.SpringBoot.service.serviceInterface.LoginUserDetailsService;
import org.apache.juli.logging.Log;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUserDetailsServiceImpl implements LoginUserDetailsService {

    @Autowired
    LoginUserDetailsRepository loginUserDetailsRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public LoginUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<LoginUserDetailsDAO> loginUserDetailsDAO = loginUserDetailsRepository.findByUsername(userName);

        loginUserDetailsDAO.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        LoginUserDetails loginUserDetails = convertToLoginUserDetails(loginUserDetailsDAO);
        loginUserDetails.setAuthorities();

        return loginUserDetails;
    }

    private LoginUserDetails convertToLoginUserDetails(Optional<LoginUserDetailsDAO> loginUserDetailsDAO)
    {
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        LoginUserDetailsDAO convertibleUserDetailsDAO = loginUserDetailsDAO.stream().findFirst().orElse(null);

        LoginUserDetails loginUserDetails = modelMapper.map(convertibleUserDetailsDAO, LoginUserDetails.class);

        return loginUserDetails;
    }
}
