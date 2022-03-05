package com.pinguin.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pinguin.entity.Bug;
import com.pinguin.entity.Developer;
import com.pinguin.entity.Story;
import com.pinguin.exception.ResourceNotFoundException;
import com.pinguin.model.api.Assignment;
import com.pinguin.model.enums.Priority;
import com.pinguin.model.enums.BugStatus;
import com.pinguin.model.enums.StoryStatus;
import com.pinguin.service.serviceInterface.BugsService;
import com.pinguin.service.serviceInterface.DevelopersService;
import com.pinguin.service.serviceInterface.StoriesService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DevelopersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DevelopersService developersService;

    private Developer developerOne, developerTwo;

    @BeforeEach
    public void init() {
        developerOne = new Developer();

        developerOne.setDeveloperId(UUID.randomUUID());
        developerOne.setName("Sid");

        developerTwo = new Developer();

        developerTwo.setDeveloperId(UUID.randomUUID());
        developerTwo.setName("Andrew");

    }

    @AfterEach
    public void teardown() {
        developerOne = null;
        developerTwo = null;
    }

    @Test
    public void shouldReturnDevelopers() throws Exception {

        when(developersService.getDevelopers("")).thenReturn(new ArrayList<>(Arrays.asList(developerOne, developerTwo)));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/developers")
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("name", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sid"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Andrew"));
    }

    @Test
    public void shouldReturnDevelopersWithName() throws Exception {

        when(developersService.getDevelopers("An")).thenReturn(new ArrayList<>(Arrays.asList(developerTwo)));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/developers")
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("name", "An"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Andrew"));
    }

    @Test
    public void shouldNotReturnDeveloperWithName() throws Exception {

        when(developersService.getDevelopers("An")).thenReturn(new ArrayList<>(Arrays.asList(developerTwo)));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/developers")
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("name", "Sid"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void shouldFindDeveloperWithDeveloperId() throws Exception {

        when(developersService.getDeveloperById(developerOne.getDeveloperId())).thenReturn(developerOne);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/developers/" + developerOne.getDeveloperId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sid"));

    }

    @Test
    public void shouldNotFindDeveloperWithDeveloperId() throws Exception {

        UUID randomId = UUID.randomUUID();

        when(developersService.getDeveloperById(randomId)).thenThrow(ResourceNotFoundException.class);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/developers/" + randomId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
    }

    @Test
    public void shouldCreateDeveloper() throws Exception {

        Developer developer = new Developer();

        developer.setName("John");

        Gson gson = new Gson();
        String json = gson.toJson(developer);

        when(developersService.createDeveloper(any(Developer.class))).thenReturn(developer);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/developers")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"));

    }

    @Test
    public void shouldUpdateDeveloper() throws Exception {

        developerOne.setName("Siddharth");

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

        String json = gson.toJson(developerOne);

        when(developersService.updateDeveloper(any(Developer.class))).thenReturn(developerOne);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/developers")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Siddharth"));

    }


    @Test
    public void shouldDeleteDeveloper() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/developers/" + developerOne.getDeveloperId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
