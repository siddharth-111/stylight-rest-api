//package com.stylight.service;
//
//import com.stylight.entity.Bug;
//import com.stylight.exception.ResourceNotFoundException;
//import com.stylight.model.api.Assignment;
//import com.stylight.model.enums.Priority;
//import com.stylight.model.enums.BugStatus;
//import com.stylight.repository.BugsRepository;
//import com.stylight.repository.DevelopersRepository;
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
//public class BugsServiceTest {
//
//    @Autowired
//    BugsService bugsService;
//
//    @MockBean
//    private BugsRepository bugsRepository;
//
//    @MockBean
//    private DevelopersRepository developersRepository;
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
//    }
//
//    @AfterEach
//    public void teardown() {
//        bugsService.deleteAll();
//    }
//
//    @Test
//    public void shouldFetchBugs() {
//
//        when(bugsRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(bugOne, bugTwo)));
//
//        List<Bug> bugList = bugsService.getBugs("");
//
//        assertEquals(bugList.size(), 2);
//
//        assertEquals(bugList.get(0).getTitle(), "Bug title");
//        assertEquals(bugList.get(0).getDescription(), "Bug Description");
//        assertEquals(bugList.get(0).getBugStatus(), BugStatus.NEW);
//        assertEquals(bugList.get(0).getPriority(), Priority.MINOR);
//
//        assertEquals(bugList.get(1).getTitle(), "Bug title two");
//        assertEquals(bugList.get(1).getDescription(), "Bug Description two");
//        assertEquals(bugList.get(1).getBugStatus(), BugStatus.NEW);
//        assertEquals(bugList.get(1).getPriority(), Priority.CRITICAL);
//    }
//
//    @Test
//    public void shouldFetchBugsByTitle() {
//
//        when(bugsRepository.findByTitleContaining("two")).thenReturn(new ArrayList<>(Arrays.asList(bugTwo)));
//
//        List<Bug> bugList = bugsService.getBugs("two");
//
//        assertEquals(bugList.size(), 1);
//
//        assertEquals(bugList.get(0).getTitle(), "Bug title two");
//        assertEquals(bugList.get(0).getDescription(), "Bug Description two");
//        assertEquals(bugList.get(0).getBugStatus(), BugStatus.NEW);
//        assertEquals(bugList.get(0).getPriority(), Priority.CRITICAL);
//    }
//
//    @Test
//    public void shouldFetchBugById() {
//
//        when(bugsRepository.findById(bugOne.getIssueId())).thenReturn(java.util.Optional.ofNullable(bugOne));
//
//        Bug bug = bugsService.getBugById(bugOne.getIssueId());
//
//        assertEquals(bug.getTitle(), "Bug title");
//        assertEquals(bug.getDescription(), "Bug Description");
//        Assertions.assertEquals(bug.getBugStatus(), BugStatus.NEW);
//        Assertions.assertEquals(bug.getPriority(), Priority.MINOR);
//    }
//
//    @Test
//    public void shouldNotFetchBugById() {
//        UUID randomId = UUID.randomUUID();
//        when(bugsRepository.findById(randomId)).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> bugsService.getBugById(randomId));
//    }
//
//    @Test
//    public void shouldCreateBug() {
//        Bug bug = new Bug();
//
//        bug.setTitle("Bug title 1");
//        bug.setDescription("Bug Description 1");
//        bug.setBugStatus(BugStatus.VERIFIED);
//        bug.setPriority(Priority.MAJOR);
//
//        when(bugsRepository.save(bug)).thenReturn(bug);
//
//        Bug createdBug = bugsService.createBug(bug);
//
//        assertEquals(createdBug.getTitle(), "Bug title 1");
//        assertEquals(createdBug.getDescription(), "Bug Description 1");
//        Assertions.assertEquals(createdBug.getBugStatus(), BugStatus.VERIFIED);
//        Assertions.assertEquals(createdBug.getPriority(), Priority.MAJOR);
//    }
//
//    @Test
//    public void shouldUpdateBug() {
//        bugOne.setDescription("Bug Description updated");
//        bugOne.setPriority(Priority.CRITICAL);
//
//        when(bugsRepository.findById(bugOne.getIssueId())).thenReturn(java.util.Optional.ofNullable(bugOne));
//        when(bugsRepository.save(bugOne)).thenReturn(bugOne);
//
//        Bug updatedBug = bugsService.updateBug(bugOne);
//
//        assertEquals(updatedBug.getTitle(), "Bug title");
//        assertEquals(updatedBug.getDescription(), "Bug Description updated");
//        Assertions.assertEquals(updatedBug.getBugStatus(), BugStatus.NEW);
//        Assertions.assertEquals(updatedBug.getPriority(), Priority.CRITICAL);
//    }
//
//    @Test
//    public void shouldNotUpdateBug() {
//
//        when(bugsRepository.save(bugOne)).thenReturn(bugOne);
//
//        assertThrows(ResourceNotFoundException.class, () -> bugsService.updateBug(bugOne));
//    }
//
//    @Test
//    public void shouldDeleteBugById() {
//        when(bugsRepository.findById(bugOne.getIssueId())).thenReturn(java.util.Optional.ofNullable(bugOne));
//        bugsService.deleteBug(bugOne.getIssueId());
//        verify(bugsRepository, times(1)).deleteById(bugOne.getIssueId());
//    }
//
//    @Test
//    public void shouldNotDeleteBugById() {
//        UUID randomId = UUID.randomUUID();
//        when(bugsRepository.findById(randomId)).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> bugsService.deleteBug(randomId));
//    }
//
//    @Test
//    public void shouldDeleteAllBugs() {
//        bugsService.deleteAll();
//        verify(bugsRepository, times(1)).deleteAll();
//    }
//
//    @Test
//    public void shouldAssignBug() {
//        Developer developer = new Developer();
//        developer.setDeveloperId(UUID.randomUUID());
//        developer.setName("Andrew");
//
//        List<Assignment> assignmentList = new ArrayList<>();
//        Assignment assignment = new Assignment();
//        assignment.setDeveloperId(developer.getDeveloperId());
//        assignment.setIssueId(bugOne.getIssueId());
//
//        assignmentList.add(assignment);
//
//        when(bugsRepository.findById(bugOne.getIssueId())).thenReturn(java.util.Optional.ofNullable(bugOne));
//        when(developersRepository.findById(developer.getDeveloperId())).thenReturn(java.util.Optional.ofNullable(developer));
//
//        bugsService.assign(assignmentList);
//
//        verify(bugsRepository, times(1)).save(bugOne);
//    }
//
//    @Test
//    public void shouldNotAssignBugWithEmptyAssignments() {
//        Developer developer = new Developer();
//        developer.setDeveloperId(UUID.randomUUID());
//        developer.setName("Andrew");
//
//        List<Assignment> assignmentList = new ArrayList<>();
//
//        when(bugsRepository.findById(bugOne.getIssueId())).thenReturn(java.util.Optional.ofNullable(bugOne));
//        when(developersRepository.findById(developer.getDeveloperId())).thenReturn(java.util.Optional.ofNullable(developer));
//
//        bugsService.assign(assignmentList);
//
//        verify(bugsRepository, times(0)).save(bugOne);
//    }
//
//    @Test
//    public void shouldNotAssignBugAsNoBugWithIdIsFound() {
//        Developer developer = new Developer();
//        developer.setDeveloperId(UUID.randomUUID());
//        developer.setName("Andrew");
//
//        List<Assignment> assignmentList = new ArrayList<>();
//        Assignment assignment = new Assignment();
//        assignment.setDeveloperId(developer.getDeveloperId());
//        assignment.setIssueId(bugOne.getIssueId());
//
//        assignmentList.add(assignment);
//
//        when(bugsRepository.findById(bugOne.getIssueId())).thenThrow(ResourceNotFoundException.class);
//        when(developersRepository.findById(developer.getDeveloperId())).thenReturn(java.util.Optional.ofNullable(developer));
//
//        assertThrows(ResourceNotFoundException.class, () -> bugsService.assign(assignmentList));
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
//        assignment.setIssueId(bugOne.getIssueId());
//
//        assignmentList.add(assignment);
//
//        when(bugsRepository.findById(bugOne.getIssueId())).thenReturn(java.util.Optional.ofNullable(bugOne));
//        when(developersRepository.findById(developer.getDeveloperId())).thenThrow(ResourceNotFoundException.class);
//
//        assertThrows(ResourceNotFoundException.class, () -> bugsService.assign(assignmentList));
//    }
//}
