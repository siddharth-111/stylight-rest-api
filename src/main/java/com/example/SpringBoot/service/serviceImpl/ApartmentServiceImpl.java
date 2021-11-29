package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.Apartment;
import com.example.SpringBoot.dao.ApartmentDAO;
import com.example.SpringBoot.dao.LoginUserDetailsDAO;
import com.example.SpringBoot.repository.ApartmentRepository;
import com.example.SpringBoot.repository.LoginUserDetailsRepository;
import com.example.SpringBoot.service.utils.ServiceHelper;
import com.example.SpringBoot.service.serviceInterface.ApartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    ApartmentRepository apartmentRepository;

    @Autowired
    LoginUserDetailsRepository loginUserDetailsRepository;

    @Autowired
    ServiceHelper serviceHelper;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Apartment createApartment(Apartment apartment, long userid) {

        ApartmentDAO apartmentDAO = convertToApartmentDAO(apartment);

        Optional<LoginUserDetailsDAO> loginUserDetailsDAO = loginUserDetailsRepository.findById(userid);

        apartmentDAO.setCreatedUser(loginUserDetailsDAO.stream().findFirst().orElse(null));

        ApartmentDAO apartmentDAOResponse = apartmentRepository.save(apartmentDAO);

        Apartment apartmentResponse = convertToApartment(apartmentDAOResponse);

        return apartmentResponse;
    }

    private ApartmentDAO convertToApartmentDAO(Apartment apartment)
    {
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        ApartmentDAO apartmentDAO = modelMapper.map(apartment, ApartmentDAO.class);

        return apartmentDAO;
    }

    private Apartment convertToApartment(ApartmentDAO apartmentDAO)
    {
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        Apartment apartment = modelMapper.map(apartmentDAO, Apartment.class);

        return apartment;
    }
}
