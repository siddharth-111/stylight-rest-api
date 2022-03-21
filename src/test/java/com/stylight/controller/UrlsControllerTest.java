package com.stylight.controller;

import com.google.gson.Gson;
import com.stylight.entity.Url;
import com.stylight.service.serviceInterface.UrlService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlService;

    private Url urlOne, urlTwo;

    @BeforeEach
    public void init() {
        urlOne = new Url();

        urlOne.setPrettyUrl("/Fashion");
        urlOne.setOrderedParameter("/products");

        urlTwo = new Url();

        urlTwo.setPrettyUrl("/Women/");
        urlTwo.setOrderedParameter("/products?gender=female");

    }

    @AfterEach
    public void teardown() {
        urlOne = null;
        urlTwo = null;
    }

    @Test
    public void shouldReturnPrettyUrls() throws Exception {
        List<String> orderedParameters = new LinkedList<>(Arrays.asList("/products", "/products?gender=female"));
        when(urlService.getPrettyUrl(orderedParameters)).thenReturn(new ArrayList<>(Arrays.asList(urlOne, urlTwo)));


        Gson gson = new Gson();
        String json = gson.toJson(orderedParameters);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/urls/prettyUrl")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderedParameter").value("/products"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].prettyUrl").value("/Fashion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].orderedParameter").value("/products?gender=female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].prettyUrl").value("/Women/"));
    }

    @Test
    public void shouldReturnOrderedParameters() throws Exception {
        List<String> prettyUrls = new LinkedList<>(Arrays.asList("/Fashion", "/Women/"));
        when(urlService.getOrderedParameters(prettyUrls)).thenReturn(new ArrayList<>(Arrays.asList(urlOne, urlTwo)));


        Gson gson = new Gson();
        String json = gson.toJson(prettyUrls);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/urls/orderedParameters")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderedParameter").value("/products"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].prettyUrl").value("/Fashion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].orderedParameter").value("/products?gender=female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].prettyUrl").value("/Women/"));
    }

    @Test
    public void shouldCreateMappedUrl() throws Exception {

        Url mappedUrl = new Url("/products?brand=123","/Adidas/");;

        Gson gson = new Gson();
        String json = gson.toJson(mappedUrl);

        when(urlService.createMapping(any(Url.class))).thenReturn(mappedUrl);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderedParameter").value("/products?brand=123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.prettyUrl").value("/Adidas/"));
    }

}
