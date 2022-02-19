package com.ratepay.challenge.service;

import com.ratepay.challenge.entity.Bug;
import com.ratepay.challenge.exception.ResourceNotFoundException;
import com.ratepay.challenge.model.enums.Priority;
import com.ratepay.challenge.model.enums.Status;
import com.ratepay.challenge.service.serviceInterface.BugsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BugsServiceTest {

    @Autowired
    BugsService bugsService;

    private Bug createdBug;
    private Bug bug;

    @BeforeEach
    public void init() {
        bug = new Bug();

        bug.setTitle("Bug title");
        bug.setDescription("Bug Description");
        bug.setStatus(Status.NEW);
        bug.setPriority(Priority.MINOR);

        createdBug = bugsService.createBug(bug);
    }

    @AfterEach
    public void teardown() {
        bugsService.deleteAll();
    }

    @Test
    public void shouldFetchBugs() {
        List<Bug> bugList = bugsService.getBugs();

        assertEquals(bugList.size(), 1);

        assertEquals(bugList.get(0).getTitle(), "Bug title");
        assertEquals(bugList.get(0).getDescription(), "Bug Description");
        assertEquals(bugList.get(0).getStatus(), Status.NEW);
        assertEquals(bugList.get(0).getPriority(), Priority.MINOR);
    }

    @Test
    public void shouldFetchBugById() {
        Bug bug = bugsService.getBugById(createdBug.getIssueId());

        assertEquals(bug.getTitle(), "Bug title");
        assertEquals(bug.getDescription(), "Bug Description");
        assertEquals(bug.getStatus(), Status.NEW);
        assertEquals(bug.getPriority(), Priority.MINOR);
    }

    @Test
    public void shouldNotFetchBugById() {
        assertThrows(ResourceNotFoundException.class, () -> bugsService.getBugById(UUID.randomUUID()));
    }

    @Test
    public void shouldCreateBug() {
        Bug bug = new Bug();

        bug.setTitle("Bug title 1");
        bug.setDescription("Bug Description 1");
        bug.setStatus(Status.VERIFIED);
        bug.setPriority(Priority.MAJOR);

        Bug createdBug = bugsService.createBug(bug);

        assertEquals(createdBug.getTitle(), "Bug title 1");
        assertEquals(createdBug.getDescription(), "Bug Description 1");
        assertEquals(createdBug.getStatus(), Status.VERIFIED);
        assertEquals(createdBug.getPriority(), Priority.MAJOR);
    }

    @Test
    public void shouldUpdateBug() {
        bug.setDescription("Bug Description updated");
        bug.setPriority(Priority.CRITICAL);

        Bug updatedBug = bugsService.updateBug(bug);

        assertEquals(updatedBug.getTitle(), "Bug title");
        assertEquals(updatedBug.getDescription(), "Bug Description updated");
        assertEquals(updatedBug.getStatus(), Status.NEW);
        assertEquals(updatedBug.getPriority(), Priority.CRITICAL);
    }

    @Test
    public void shouldDeleteBugById() {
        Bug bug = new Bug();

        bug.setTitle("Bug title");
        bug.setDescription("Bug to be deleted");
        bug.setStatus(Status.NEW);
        bug.setPriority(Priority.CRITICAL);

        Bug createdBug = bugsService.createBug(bug);

        assertEquals(createdBug.getTitle(), "Bug title");
        assertEquals(createdBug.getDescription(), "Bug to be deleted");
        assertEquals(createdBug.getStatus(), Status.NEW);
        assertEquals(createdBug.getPriority(), Priority.CRITICAL);

        bugsService.deleteBug(createdBug.getIssueId());

        assertThrows(ResourceNotFoundException.class, () -> bugsService.getBugById(createdBug.getIssueId()));
    }

    @Test
    public void shouldDeleteAllBugs() {
        bugsService.deleteAll();

        List<Bug> bugList = bugsService.getBugs();

        assertEquals(bugList.size() , 0);
    }
}
