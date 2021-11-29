package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.LoginUserDetails;
import com.example.SpringBoot.dao.LoginUserDetailsDAO;
import com.example.SpringBoot.repository.LoginUserDetailsRepository;
import com.example.SpringBoot.service.utils.ServiceHelper;
import com.example.SpringBoot.service.serviceInterface.LoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUserDetailsServiceImpl implements LoginUserDetailsService {

    @Autowired
    LoginUserDetailsRepository loginUserDetailsRepository;

    @Autowired
    private ServiceHelper serviceHelper;


    @Override
    public LoginUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<LoginUserDetailsDAO> loginUserDetailsDAO = loginUserDetailsRepository.findByUsername(userName);

        loginUserDetailsDAO.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        LoginUserDetails loginUserDetails = serviceHelper.convertToLoginUserDetails(loginUserDetailsDAO);
        loginUserDetails.setAuthorities();

        return loginUserDetails;
    }

    @Override
    public LoginUserDetails createLoginUser(LoginUserDetails loginUserDetails) throws Exception {

        LoginUserDetailsDAO loginUserDetailsDAOResponse = loginUserDetailsRepository.save(serviceHelper.convertToLoginUserDetailsDAO(loginUserDetails));

        LoginUserDetails loginUserDetailsResponse = serviceHelper.convertToLoginUserDetails(Optional.of(loginUserDetailsDAOResponse));
        loginUserDetailsResponse.setAuthorities();

        return loginUserDetailsResponse;
    }

}
