package com.example.SpringBoot.controller;

import com.example.SpringBoot.Model.Tutorial;
import com.example.SpringBoot.config.TestSecurityConfig;
import com.example.SpringBoot.controller.utils.ControllerTestHelper;
import com.example.SpringBoot.repository.TutorialRepository;
import com.example.SpringBoot.service.serviceImpl.TutorialServiceImpl;
import com.example.SpringBoot.service.serviceInterface.TutorialService;
import com.example.SpringBoot.service.utils.ServiceHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TestSecurityConfig.class)
@AutoConfigureMockMvc
public class TutorialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorialServiceImpl tutorialService;

    @Autowired
    private ControllerTestHelper controllerTestHelper;

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

    @Test
    public void shouldCreateTutorial() throws Exception {
        String title = "hello";

        Tutorial tutorial = new Tutorial();
        tutorial.setTitle("hello");
        tutorial.setDescription("world");
        tutorial.setPublished(false);

        List<Tutorial> tutorials = new ArrayList<Tutorial>();
        tutorials.add(tutorial);

        String json = controllerTestHelper.convertTutorialToJsonString(tutorial);

        when(tutorialService.createTutorial(tutorial)).thenReturn(tutorial);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/tutorials")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}