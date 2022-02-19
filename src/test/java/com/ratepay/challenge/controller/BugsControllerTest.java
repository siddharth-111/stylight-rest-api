package com.ratepay.challenge.controller;

import com.google.gson.Gson;
import com.ratepay.challenge.entity.Bug;
import com.ratepay.challenge.exception.ResourceNotFoundException;
import com.ratepay.challenge.model.enums.Priority;
import com.ratepay.challenge.model.enums.Status;
import com.ratepay.challenge.service.serviceInterface.BugsService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BugsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BugsService bugsService;

    private Bug bugResponse;
    private Bug bug;

    @BeforeEach
    public void init() {
        bug = new Bug();

        bug.setTitle("Bug title");
        bug.setDescription("Bug Description");
        bug.setStatus(Status.NEW);
        bug.setPriority(Priority.MINOR);

        bugResponse = bugsService.createBug(bug);
    }

    @AfterEach
    public void teardown() {
        bugsService.deleteAll();
    }

    @Test
    public void shouldReturnBugs() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/bugs")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Bug title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Bug Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value(Status.NEW.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].priority").value(Priority.MINOR.toString()));
    }

    @Test
    public void shouldFindBugWithIssueId() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/bugs/" + bugResponse.getIssueId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bug title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Bug Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Status.NEW.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(Priority.MINOR.toString()));

    }

    @Test
    public void shouldNotFindBugWithIssueId() throws Exception {

        UUID randomId = UUID.randomUUID();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/bugs/" + randomId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Not found issue with id = " + randomId, result.getResolvedException().getMessage()));
    }

    @Test
    public void shouldCreateBug() throws Exception {

        Bug bug = new Bug();

        bug.setTitle("Bug title");
        bug.setDescription("Bug Description");
        bug.setStatus(Status.NEW);
        bug.setPriority(Priority.MINOR);

        Gson gson = new Gson();
        String json = gson.toJson(bug);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/bugs/")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bug title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Bug Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Status.NEW.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(Priority.MINOR.toString()));

    }
}
