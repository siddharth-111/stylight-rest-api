package com.example.SpringBoot.controller;

import com.example.SpringBoot.Model.LoginUserDetails;
import com.example.SpringBoot.service.serviceInterface.LoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class LoginUserController {

    @Autowired
    LoginUserDetailsService loginUserDetailsService;

    @PostMapping("/user/create")
    public ResponseEntity<LoginUserDetails> createLoginUser(@RequestBody LoginUserDetails loginUserDetails) {
        try
        {
            LoginUserDetails loginUserDetailsResponse = loginUserDetailsService.createLoginUser(loginUserDetails);
            return new ResponseEntity<>(loginUserDetailsResponse, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
