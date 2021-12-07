//package com.example.SpringBoot.service;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import static org.junit.jupiter.api.Assertions.*;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.mockito.Mockito.when;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class TutorialServiceTest {
//
//    @Autowired
//    private TutorialServiceImpl tutorialService;
//
//    @MockBean
//    private TutorialRepository tutorialRepository;
//
//    private Tutorial tutorial, tutorial1;
//    private List<Tutorial> tutorials;
//
//    @BeforeEach
//    public void setUp() {
//        tutorial = new Tutorial();
//        tutorial.setTitle("Beethoven");
//        tutorial.setDescription("1");
//        tutorial.setPublished(true);
//
//        tutorial1 = new Tutorial();
//        tutorial1.setTitle("Seth");
//        tutorial1.setDescription("2");
//        tutorial1.setPublished(false);
//
//        tutorials = new ArrayList<Tutorial>();
//        tutorials.add(tutorial);
//        tutorials.add(tutorial1);
//    }
//
//    @AfterEach
//    public void tearDown() {
//        tutorial = tutorial1 = null;
//        tutorials = null;
//    }
//
//    @Test
//    public void shouldReturnMultipleTutorials() throws Exception {
//        when(tutorialRepository.findAll()).thenReturn(tutorials);
//
//        List<Tutorial> response = tutorialService.getAllTutorials(null);
//
//        assertEquals(response.size(), 2);
//        assertEquals(response.get(0).getTitle(), "Beethoven");
//        assertEquals(response.get(1).getTitle(), "Seth");
//        assertEquals(response.get(0).getDescription(), "1");
//        assertEquals(response.get(1).getDescription(), "2");
//    }
//
//    @Test
//    public void shouldNotReturnTutorial() throws Exception {
//        when(tutorialRepository.findAll()).thenReturn(tutorials);
//
//        List<Tutorial> response = tutorialService.getAllTutorials("Charles");
//
//        Assertions.assertEquals(response.size(), 0);
//    }
//
//    @Test
//    public void shouldReturnTutorialByTitle() throws Exception {
//        String title = "Seth";
//        when(tutorialRepository.findByTitleContaining(title)).thenReturn(tutorials.stream().filter(x -> x.getTitle().contains(title)).collect(Collectors.toList()));
//
//        List<Tutorial> response = tutorialService.getAllTutorials(title);
//
//        Assertions.assertEquals(response.size(), 1);
//        assertEquals(response.get(0).getTitle(), "Seth");
//        assertEquals(response.get(0).getDescription(), "2");
//    }
//
//    @Test
//    public void shouldNotReturnTutorialByTitle() throws Exception {
//        String title = "JK";
//        when(tutorialRepository.findByTitleContaining(title)).thenReturn(tutorials.stream().filter(x -> x.getTitle().contains(title)).collect(Collectors.toList()));
//
//        List<Tutorial> response = tutorialService.getAllTutorials(title);
//
//        assertEquals(response.size(), 0);
//    }
//}
