package com.example.SpringBoot.controller;

import com.example.SpringBoot.Model.Tutorial;
import com.example.SpringBoot.repository.TutorialRepository;
import com.example.SpringBoot.service.serviceImpl.TutorialServiceImpl;
import com.example.SpringBoot.service.serviceInterface.TutorialService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TutorialController.class)
public class TutorialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorialServiceImpl tutorialService;

    @Test
    public void shouldReturnMultipleTutorial() throws Exception {
        Tutorial tutorial = new Tutorial();
        tutorial.setTitle("hello");
        tutorial.setDescription("world");
        tutorial.setPublished(false);

        List<Tutorial> tutorials = new ArrayList<Tutorial>();
        tutorials.add(tutorial);

        when(tutorialService.getAllTutorials("")).thenReturn(tutorials);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/tutorials")
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("title", ""))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("hello"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("world"));
    }

    @Test
    public void shouldReturnEmptyTutorial() throws Exception {
        when(tutorialService.getAllTutorials("test")).thenReturn(new ArrayList<Tutorial>());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/tutorials")
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("title", "test"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnSingleTutorial() throws Exception {
        String title = "hello";

        Tutorial tutorial = new Tutorial();
        tutorial.setTitle("hello");
        tutorial.setDescription("world");
        tutorial.setPublished(false);

        List<Tutorial> tutorials = new ArrayList<Tutorial>();
        tutorials.add(tutorial);

        when(tutorialService.getAllTutorials(title)).thenReturn(tutorials);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/tutorials")
                        .queryParam("title", title)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("hello"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("world"));
    }
}