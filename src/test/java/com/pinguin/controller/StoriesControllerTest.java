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
public class StoriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoriesService storiesService;

    private Story storyOne, storyTwo;

    @BeforeEach
    public void init() {
        storyOne = new Story();

        storyOne.setIssueId(UUID.randomUUID());
        storyOne.setTitle("Story title");
        storyOne.setDescription("Story Description");
        storyOne.setStoryStatus(StoryStatus.NEW);
        storyOne.setPoints(5);

        storyTwo = new Story();

        storyTwo.setIssueId(UUID.randomUUID());
        storyTwo.setTitle("Story title two");
        storyTwo.setDescription("Story Description two");
        storyTwo.setStoryStatus(StoryStatus.ESTIMATED);
        storyTwo.setPoints(8);

    }

    @AfterEach
    public void teardown() {
        storyOne = null;
        storyTwo = null;
    }

    @Test
    public void shouldReturnStories() throws Exception {

        when(storiesService.getStories("")).thenReturn(new ArrayList<>(Arrays.asList(storyOne, storyTwo)));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/stories")
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("title", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Story title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Story Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].storyStatus").value(StoryStatus.NEW.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].points").value(5));
    }

    @Test
    public void shouldReturnStoriesWithTitle() throws Exception {

        when(storiesService.getStories("Story title two")).thenReturn(new ArrayList<>(Arrays.asList(storyTwo)));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/stories")
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("title", "Story title two"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Story title two"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Story Description two"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].storyStatus").value(StoryStatus.ESTIMATED.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].points").value(8));
    }

    @Test
    public void shouldNotReturnStoriesWithTitle() throws Exception {

        when(storiesService.getStories("Story title two")).thenReturn(new ArrayList<>(Arrays.asList(storyTwo)));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/bugs")
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("title", "Story no title"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void shouldFindStoryWithIssueId() throws Exception {

        when(storiesService.getStoryById(storyOne.getIssueId())).thenReturn(storyOne);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/stories/" + storyOne.getIssueId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Story title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Story Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.storyStatus").value(StoryStatus.NEW.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.points").value(5));

    }

    @Test
    public void shouldNotFindStoryWithIssueId() throws Exception {

        UUID randomId = UUID.randomUUID();

        when(storiesService.getStoryById(randomId)).thenThrow(ResourceNotFoundException.class);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/stories/" + randomId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
    }

    @Test
    public void shouldCreateStory() throws Exception {

        Story story = new Story();

        story.setTitle("Story title three");
        story.setDescription("Story Description three");
        story.setStoryStatus(StoryStatus.NEW);
        story.setPoints(6);

        Gson gson = new Gson();
        String json = gson.toJson(story);

        when(storiesService.createStory(any(Story.class))).thenReturn(story);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/stories")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Story title three"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Story Description three"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.storyStatus").value(StoryStatus.NEW.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.points").value(6));

    }

    @Test
    public void shouldUpdateBug() throws Exception {

        storyOne.setDescription("Story Description updated");
        storyOne.setStoryStatus(StoryStatus.ESTIMATED);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

        String json = gson.toJson(storyOne);

        when(storiesService.updateStory(any(Story.class))).thenReturn(storyOne);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/stories/")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Story title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Story Description updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.storyStatus").value(StoryStatus.ESTIMATED.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.points").value(5));

    }


    @Test
    public void shouldDeleteBug() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/stories/" + storyOne.getIssueId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAssignBug() throws Exception {

        Developer developer = new Developer();
        developer.setDeveloperId(UUID.randomUUID());
        developer.setName("Andrew");

        List<Assignment> assignmentList = new ArrayList<>();
        Assignment assignment = new Assignment();
        assignment.setDeveloperId(developer.getDeveloperId());
        assignment.setIssueId(storyOne.getIssueId());

        Gson gson = new Gson();
        String json = gson.toJson(assignmentList);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/stories/assign")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
