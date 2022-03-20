//package com.stylight.service;
//
//
//import com.stylight.entity.Bug;
//import com.stylight.exception.ResourceNotFoundException;
//import com.stylight.model.api.Assignment;
//import com.stylight.model.enums.StoryStatus;
//import com.stylight.repository.DevelopersRepository;
//import com.stylight.repository.StoriesRepository;
//import com.stylight.service.serviceInterface.StoriesService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
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
//public class StoriesServiceTest {
//
//    @Autowired
//    StoriesService storiesService;
//
//    @MockBean
//    private StoriesRepository storiesRepository;
//
//    @MockBean
//    private DevelopersRepository developersRepository;
//
//    private Story storyOne, storyTwo;
//
//    @BeforeEach
//    public void init() {
//        storyOne = new Story();
//
//        storyOne.setIssueId(UUID.randomUUID());
//        storyOne.setTitle("Story title");
//        storyOne.setDescription("Story Description");
//        storyOne.setStoryStatus(StoryStatus.NEW);
//        storyOne.setPoints(5);
//
//        storyTwo = new Story();
//
//        storyTwo.setIssueId(UUID.randomUUID());
//        storyTwo.setTitle("Story title two");
//        storyTwo.setDescription("Story Description two");
//        storyTwo.setStoryStatus(StoryStatus.ESTIMATED);
//        storyTwo.setPoints(8);
//
//    }
//
//    @AfterEach
//    public void teardown() {
//        storyOne = null;
//        storyTwo = null;
//    }
//
//    @Test
//    public void shouldFetchStories() {
//
//        when(storiesRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(storyOne, storyTwo)));
//
//        List<Story> storyList = storiesService.getStories("");
//
//        assertEquals(storyList.size(), 2);
//
//        assertEquals(storyList.get(0).getTitle(), "Story title");
//        assertEquals(storyList.get(0).getDescription(), "Story Description");
//        assertEquals(storyList.get(0).getStoryStatus(), StoryStatus.NEW);
//        assertEquals(storyList.get(0).getPoints(), 5);
//
//        assertEquals(storyList.get(1).getTitle(), "Story title two");
//        assertEquals(storyList.get(1).getDescription(), "Story Description two");
//        assertEquals(storyList.get(1).getStoryStatus(), StoryStatus.ESTIMATED);
//        assertEquals(storyList.get(1).getPoints(), 8);
//    }
//
//    @Test
//    public void shouldFetchStoriesByTitle() {
//
//        when(storiesRepository.findByTitleContaining("two")).thenReturn(new ArrayList<>(Arrays.asList(storyTwo)));
//
//        List<Story> storyList = storiesService.getStories("two");
//
//        assertEquals(storyList.size(), 1);
//
//        assertEquals(storyList.get(0).getTitle(), "Story title two");
//        assertEquals(storyList.get(0).getDescription(), "Story Description two");
//        assertEquals(storyList.get(0).getStoryStatus(), StoryStatus.ESTIMATED);
//        assertEquals(storyList.get(0).getPoints(), 8);
//    }
//
//    @Test
//    public void shouldFetchBugById() {
//
//        when(storiesRepository.findById(storyOne.getIssueId())).thenReturn(java.util.Optional.ofNullable(storyOne));
//
//        Story story = storiesService.getStoryById(storyOne.getIssueId());
//
//        assertEquals(story.getTitle(), "Story title");
//        assertEquals(story.getDescription(), "Story Description");
//        assertEquals(story.getStoryStatus(), StoryStatus.NEW);
//        assertEquals(story.getPoints(), 5);
//    }
//
//    @Test
//    public void shouldNotFetchStoryById() {
//        UUID randomId = UUID.randomUUID();
//        when(storiesRepository.findById(randomId)).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> storiesService.getStoryById(randomId));
//    }
//
//    @Test
//    public void shouldCreateBug() {
//        Story story = new Story();
//
//        story.setTitle("Story title 1");
//        story.setDescription("Story Description 1");
//        story.setStoryStatus(StoryStatus.COMPLETED);
//        story.setPoints(8);
//
//        when(storiesRepository.save(story)).thenReturn(story);
//
//        Story createdStory = storiesService.createStory(story);
//
//        assertEquals(createdStory.getTitle(), "Story title 1");
//        assertEquals(createdStory.getDescription(), "Story Description 1");
//        Assertions.assertEquals(createdStory.getStoryStatus(), StoryStatus.COMPLETED);
//        Assertions.assertEquals(createdStory.getPoints(), 8);
//    }
//
//    @Test
//    public void shouldUpdateStory() {
//        storyOne.setDescription("Story Description updated");
//        storyOne.setPoints(7);
//
//        when(storiesRepository.findById(storyOne.getIssueId())).thenReturn(java.util.Optional.ofNullable(storyOne));
//        when(storiesRepository.save(storyOne)).thenReturn(storyOne);
//
//        Story updatedStory = storiesService.updateStory(storyOne);
//
//        assertEquals(updatedStory.getDescription(), "Story Description updated");
//        Assertions.assertEquals(updatedStory.getPoints(), 7);
//    }
//
//    @Test
//    public void shouldNotUpdateStory() {
//
//        when(storiesRepository.save(storyOne)).thenReturn(storyOne);
//
//        assertThrows(ResourceNotFoundException.class, () -> storiesService.updateStory(storyOne));
//    }
//
//    @Test
//    public void shouldDeleteStoryById() {
//        when(storiesRepository.findById(storyOne.getIssueId())).thenReturn(java.util.Optional.ofNullable(storyOne));
//        storiesService.deleteStory(storyOne.getIssueId());
//        verify(storiesRepository, times(1)).deleteById(storyOne.getIssueId());
//    }
//
//    @Test
//    public void shouldNotDeleteStoryById() {
//        UUID randomId = UUID.randomUUID();
//        when(storiesRepository.findById(randomId)).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> storiesService.deleteStory(randomId));
//    }
//
//    @Test
//    public void shouldDeleteAllStories() {
//        storiesService.deleteAll();
//        verify(storiesRepository, times(1)).deleteAll();
//    }
//
//    @Test
//    public void shouldAssignStory() {
//        Developer developer = new Developer();
//        developer.setDeveloperId(UUID.randomUUID());
//        developer.setName("Andrew");
//
//        List<Assignment> assignmentList = new ArrayList<>();
//        Assignment assignment = new Assignment();
//        assignment.setDeveloperId(developer.getDeveloperId());
//        assignment.setIssueId(storyOne.getIssueId());
//
//        assignmentList.add(assignment);
//
//        when(storiesRepository.findById(storyOne.getIssueId())).thenReturn(java.util.Optional.ofNullable(storyOne));
//        when(developersRepository.findById(developer.getDeveloperId())).thenReturn(java.util.Optional.ofNullable(developer));
//
//        storiesService.assign(assignmentList);
//
//        verify(storiesRepository, times(1)).save(storyOne);
//    }
//
//    @Test
//    public void shouldNotAssignStoryWithEmptyAssignments() {
//        Developer developer = new Developer();
//        developer.setDeveloperId(UUID.randomUUID());
//        developer.setName("Andrew");
//
//        List<Assignment> assignmentList = new ArrayList<>();
//
//        when(storiesRepository.findById(storyOne.getIssueId())).thenReturn(java.util.Optional.ofNullable(storyOne));
//        when(developersRepository.findById(developer.getDeveloperId())).thenReturn(java.util.Optional.ofNullable(developer));
//
//        storiesService.assign(assignmentList);
//
//        verify(storiesRepository, times(0)).save(storyOne);
//    }
//
//    @Test
//    public void shouldNotAssignStoryAsNoBugWithIdIsFound() {
//        Developer developer = new Developer();
//        developer.setDeveloperId(UUID.randomUUID());
//        developer.setName("Andrew");
//
//        List<Assignment> assignmentList = new ArrayList<>();
//        Assignment assignment = new Assignment();
//        assignment.setDeveloperId(developer.getDeveloperId());
//        assignment.setIssueId(storyOne.getIssueId());
//
//        assignmentList.add(assignment);
//
//        when(storiesRepository.findById(storyOne.getIssueId())).thenThrow(ResourceNotFoundException.class);
//        when(developersRepository.findById(developer.getDeveloperId())).thenReturn(java.util.Optional.ofNullable(developer));
//
//        assertThrows(ResourceNotFoundException.class, () -> storiesService.assign(assignmentList));
//    }
//
//    @Test
//    public void shouldNotAssignBugAsNoDeveloperWithIdIsFound() {
//        Developer developer = new Developer();
//        developer.setDeveloperId(UUID.randomUUID());
//        developer.setName("Andrew");
//
//        List<Assignment> assignmentList = new ArrayList<>();
//        Assignment assignment = new Assignment();
//        assignment.setDeveloperId(developer.getDeveloperId());
//        assignment.setIssueId(storyOne.getIssueId());
//
//        assignmentList.add(assignment);
//
//        when(storiesRepository.findById(storyOne.getIssueId())).thenReturn(java.util.Optional.ofNullable(storyOne));
//        when(developersRepository.findById(developer.getDeveloperId())).thenThrow(ResourceNotFoundException.class);
//
//        assertThrows(ResourceNotFoundException.class, () -> storiesService.assign(assignmentList));
//    }
//}
