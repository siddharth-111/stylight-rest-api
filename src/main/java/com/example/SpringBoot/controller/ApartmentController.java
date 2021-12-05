package com.example.SpringBoot.controller;

import com.example.SpringBoot.Model.Apartment;
import com.example.SpringBoot.service.serviceInterface.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ApartmentController {

    @Autowired
    ApartmentService apartmentService;

    @PostMapping("/apartments/{userid}")
    public ResponseEntity<Apartment> createApartment(@PathVariable("userid") long userid, @RequestBody Apartment apartment) {
        try {
            Apartment apartmentResponse = apartmentService.createApartment(apartment, userid);
            return new ResponseEntity<>(apartmentResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
