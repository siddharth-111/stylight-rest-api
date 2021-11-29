package com.example.SpringBoot.service.serviceInterface;


import com.example.SpringBoot.Model.Apartment;

public interface ApartmentService {
    Apartment createApartment(Apartment apartment, long userId) throws Exception;
}
