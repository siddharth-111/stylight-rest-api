package com.example.SpringBoot.service.utils;

import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.Model.LoginUserDetails;
import com.example.SpringBoot.dao.InstrumentDAO;
import com.example.SpringBoot.dao.LoginUserDetailsDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ServiceHelper {

    @Autowired
    ModelMapper modelMapper;

    public LoginUserDetails convertToLoginUserDetails(Optional<LoginUserDetailsDAO> loginUserDetailsDAO)
    {
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        LoginUserDetailsDAO convertibleUserDetailsDAO = loginUserDetailsDAO.stream().findFirst().orElse(null);

        LoginUserDetails loginUserDetails = modelMapper.map(convertibleUserDetailsDAO, LoginUserDetails.class);

        return loginUserDetails;
    }

    public LoginUserDetailsDAO convertToLoginUserDetailsDAO(LoginUserDetails loginUserDetails)
    {
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        LoginUserDetailsDAO loginUserDetailsDAO = modelMapper.map(loginUserDetails, LoginUserDetailsDAO.class);

        return loginUserDetailsDAO;
    }
}
