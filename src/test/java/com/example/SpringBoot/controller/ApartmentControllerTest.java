//package com.example.SpringBoot.controller;
//
//import com.example.SpringBoot.Model.Apartment;
//
//import com.example.SpringBoot.config.TestSecurityConfig;
//
//import com.example.SpringBoot.service.serviceInterface.ApartmentService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(classes = TestSecurityConfig.class)
//@AutoConfigureMockMvc
//public class ApartmentControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ApartmentService apartmentService;
//
//    private Apartment apartmentInput, apartmentResponse;
//
//    @BeforeEach
//    public void setUp() {
//        apartmentInput = new Apartment();
//        apartmentInput.setCity("Bangalore");
//        apartmentInput.setName("Apartment");
//        apartmentInput.setState("Kar");
//        apartmentInput.setStreetNum("3");
//        apartmentInput.setZipCode("560054");
//        apartmentInput.setStreet("Rudra");
//    }
//
//    @AfterEach
//    public void tearDown() {
//        apartmentInput = null;
//    }
//
//    @Test
//    public void shouldReturnApartment() throws Exception {
//        apartmentResponse= new Apartment();
//        apartmentResponse.setCity("Bangalore");
//        apartmentResponse.setName("Apartment");
//
//        apartmentResponse.setState("Kar");
//        apartmentResponse.setStreetNum("3");
//        apartmentResponse.setZipCode("560054");
//
//        when(apartmentService.createApartment(apartmentInput, 1)).thenReturn(apartmentResponse);
//
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/apartments/{userid}", "1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("hello"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("world"));
//    }
//}
