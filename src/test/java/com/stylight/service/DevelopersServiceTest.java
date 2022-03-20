//package com.stylight.service;
//
//
//import com.stylight.entity.Bug;
//import com.stylight.exception.ResourceNotFoundException;
//import com.stylight.repository.DevelopersRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class DevelopersServiceTest {
//
//    @Autowired
//    DevelopersService developersService;
//
//    @MockBean
//    private DevelopersRepository developersRepository;
//
//    private Developer developerOne, developerTwo;
//
//    @BeforeEach
//    public void init() {
//        developerOne = new Developer();
//
//        developerOne.setDeveloperId(UUID.randomUUID());
//        developerOne.setName("Sid");
//
//        developerTwo = new Developer();
//
//        developerTwo.setDeveloperId(UUID.randomUUID());
//        developerTwo.setName("Andrew");
//
//    }
//
//    @AfterEach
//    public void teardown() {
//        developerOne = null;
//        developerTwo = null;
//    }
//
//    @Test
//    public void shouldFetchDevelopers() {
//
//        when(developersRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(developerOne, developerTwo)));
//
//        List<Developer> developerList = developersService.getDevelopers("");
//
//        assertEquals(developerList.size(), 2);
//
//        assertEquals(developerList.get(0).getName(), "Sid");
//        assertEquals(developerList.get(1).getName(), "Andrew");
//
//    }
//
//    @Test
//    public void shouldFetchDevelopersByName() {
//
//        when(developersRepository.findByNameContaining("An")).thenReturn(new ArrayList<>(Arrays.asList(developerTwo)));
//
//        List<Developer> developerList = developersService.getDevelopers("An");
//
//        assertEquals(developerList.size(), 1);
//
//        assertEquals(developerList.get(0).getName(), "Andrew");
//    }
//
//    @Test
//    public void shouldFetchDeveloperById() {
//
//        when(developersRepository.findById(developerOne.getDeveloperId())).thenReturn(java.util.Optional.ofNullable(developerOne));
//
//        Developer developer = developersService.getDeveloperById(developerOne.getDeveloperId());
//
//        assertEquals(developer.getName(), "Sid");
//    }
//
//    @Test
//    public void shouldNotFetchDeveloperById() {
//        UUID randomId = UUID.randomUUID();
//        when(developersRepository.findById(randomId)).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> developersService.getDeveloperById(randomId));
//    }
//
//    @Test
//    public void shouldCreateDeveloper() {
//        Developer developer = new Developer();
//
//        developer.setName("Siddharth");
//
//        when(developersRepository.save(developer)).thenReturn(developer);
//
//        Developer createdDeveloper = developersService.createDeveloper(developer);
//
//        assertEquals(createdDeveloper.getName(), "Siddharth");
//    }
//
//    @Test
//    public void shouldUpdateDeveloper() {
//        developerOne.setName("Siddharth");
//
//        when(developersRepository.findById(developerOne.getDeveloperId())).thenReturn(java.util.Optional.ofNullable(developerOne));
//        when(developersRepository.save(developerOne)).thenReturn(developerOne);
//
//        Developer updatedDeveloper = developersService.updateDeveloper(developerOne);
//
//        assertEquals(updatedDeveloper.getName(), "Siddharth");
//    }
//
//    @Test
//    public void shouldNotUpdateDeveloper() {
//        when(developersRepository.save(developerOne)).thenReturn(developerOne);
//        assertThrows(ResourceNotFoundException.class, () -> developersService.updateDeveloper(developerOne));
//    }
//
//    @Test
//    public void shouldDeleteDeveloperById() {
//        when(developersRepository.findById(developerOne.getDeveloperId())).thenReturn(java.util.Optional.ofNullable(developerOne));
//        developersService.deleteDeveloper(developerOne.getDeveloperId());
//        verify(developersRepository, times(1)).deleteById(developerOne.getDeveloperId());
//    }
//
//    @Test
//    public void shouldNotDeleteStoryById() {
//        UUID randomId = UUID.randomUUID();
//        when(developersRepository.findById(randomId)).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> developersService.deleteDeveloper(randomId));
//    }
//
//    @Test
//    public void shouldDeleteAllStories() {
//        developersService.deleteAll();
//        verify(developersRepository, times(1)).deleteAll();
//    }
//}
