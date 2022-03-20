//package com.stylight.controller;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.stylight.entity.Bug;
//import com.stylight.exception.ResourceNotFoundException;
//import com.stylight.model.api.Assignment;
//import com.stylight.model.enums.Priority;
//import com.stylight.model.enums.BugStatus;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class BugsControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private BugsService bugsService;
//
//    private Bug bugOne, bugTwo;
//
//    @BeforeEach
//    public void init() {
//        bugOne = new Bug();
//
//        bugOne.setIssueId(UUID.randomUUID());
//        bugOne.setTitle("Bug title");
//        bugOne.setDescription("Bug Description");
//        bugOne.setBugStatus(BugStatus.NEW);
//        bugOne.setPriority(Priority.MINOR);
//
//        bugTwo = new Bug();
//
//        bugTwo.setIssueId(UUID.randomUUID());
//        bugTwo.setTitle("Bug title two");
//        bugTwo.setDescription("Bug Description two");
//        bugTwo.setBugStatus(BugStatus.NEW);
//        bugTwo.setPriority(Priority.CRITICAL);
//
//    }
//
//    @AfterEach
//    public void teardown() {
//        bugOne = null;
//        bugTwo = null;
//    }
//
//    @Test
//    public void shouldReturnBugs() throws Exception {
//
//        when(bugsService.getBugs("")).thenReturn(new ArrayList<>(Arrays.asList(bugOne, bugTwo)));
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/bugs")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .queryParam("title", ""))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Bug title"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Bug Description"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bugStatus").value(BugStatus.NEW.toString()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].priority").value(Priority.MINOR.toString()));
//    }
//
//    @Test
//    public void shouldReturnBugsWithTitle() throws Exception {
//
//        when(bugsService.getBugs("Bug title two")).thenReturn(new ArrayList<>(Arrays.asList(bugTwo)));
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/bugs")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .queryParam("title", "Bug title two"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Bug title two"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Bug Description two"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bugStatus").value(BugStatus.NEW.toString()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].priority").value(Priority.CRITICAL.toString()));
//    }
//
//    @Test
//    public void shouldNotReturnBugsWithTitle() throws Exception {
//
//        when(bugsService.getBugs("Bug title two")).thenReturn(new ArrayList<>(Arrays.asList(bugTwo)));
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/bugs")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .queryParam("title", "Bug no title"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
//    }
//    @Test
//    public void shouldFindBugWithIssueId() throws Exception {
//
//        when(bugsService.getBugById(bugOne.getIssueId())).thenReturn(bugOne);
//
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/bugs/" + bugOne.getIssueId())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bug title"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Bug Description"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.bugStatus").value(BugStatus.NEW.toString()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(Priority.MINOR.toString()));
//
//    }
//
//    @Test
//    public void shouldNotFindBugWithIssueId() throws Exception {
//
//        UUID randomId = UUID.randomUUID();
//
//        when(bugsService.getBugById(randomId)).thenThrow(ResourceNotFoundException.class);
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/bugs/" + randomId)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
//    }
//
//    @Test
//    public void shouldCreateBug() throws Exception {
//
//        Bug bug = new Bug();
//
//        bug.setTitle("Bug title three");
//        bug.setDescription("Bug Description three");
//        bug.setBugStatus(BugStatus.NEW);
//        bug.setPriority(Priority.MINOR);
//
//        Gson gson = new Gson();
//        String json = gson.toJson(bug);
//
//        when(bugsService.createBug(any(Bug.class))).thenReturn(bug);
//
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/bugs")
//                        .contentType(MediaType.APPLICATION_JSON).content(json))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bug title three"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Bug Description three"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.bugStatus").value(BugStatus.NEW.toString()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(Priority.MINOR.toString()));
//
//    }
//
//    @Test
//    public void shouldUpdateBug() throws Exception {
//
//        bugOne.setDescription("Bug Description updated");
//        bugOne.setBugStatus(BugStatus.VERIFIED);
//
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
//
//        String json = gson.toJson(bugOne);
//
//        when(bugsService.updateBug(any(Bug.class))).thenReturn(bugOne);
//
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .patch("/api/bugs/")
//                        .contentType(MediaType.APPLICATION_JSON).content(json))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bug title"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Bug Description updated"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.bugStatus").value(BugStatus.VERIFIED.toString()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(Priority.MINOR.toString()));
//
//    }
//
//
//    @Test
//    public void shouldDeleteBug() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/api/bugs/" + bugOne.getIssueId())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void shouldAssignBug() throws Exception {
//
//        Developer developer = new Developer();
//        developer.setDeveloperId(UUID.randomUUID());
//        developer.setName("Andrew");
//
//        List<Assignment> assignmentList = new ArrayList<>();
//        Assignment assignment = new Assignment();
//        assignment.setDeveloperId(developer.getDeveloperId());
//        assignment.setIssueId(bugOne.getIssueId());
//        assignmentList.add(assignment);
//
//        Gson gson = new Gson();
//        String json = gson.toJson(assignmentList);
//
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/bugs/assign")
//                        .contentType(MediaType.APPLICATION_JSON).content(json))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//}
