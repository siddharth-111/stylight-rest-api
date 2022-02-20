package com.pinguin.service;

import com.pinguin.entity.Bug;
import com.pinguin.exception.ResourceNotFoundException;
import com.pinguin.model.enums.Priority;
import com.pinguin.model.enums.BugStatus;
import com.pinguin.service.serviceInterface.BugsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
        bug.setBugStatus(BugStatus.NEW);
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
        Assertions.assertEquals(bugList.get(0).getBugStatus(), BugStatus.NEW);
        Assertions.assertEquals(bugList.get(0).getPriority(), Priority.MINOR);
    }

    @Test
    public void shouldFetchBugById() {
        Bug bug = bugsService.getBugById(createdBug.getIssueId());

        assertEquals(bug.getTitle(), "Bug title");
        assertEquals(bug.getDescription(), "Bug Description");
        Assertions.assertEquals(bug.getBugStatus(), BugStatus.NEW);
        Assertions.assertEquals(bug.getPriority(), Priority.MINOR);
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
        bug.setBugStatus(BugStatus.VERIFIED);
        bug.setPriority(Priority.MAJOR);

        Bug createdBug = bugsService.createBug(bug);

        assertEquals(createdBug.getTitle(), "Bug title 1");
        assertEquals(createdBug.getDescription(), "Bug Description 1");
        Assertions.assertEquals(createdBug.getBugStatus(), BugStatus.VERIFIED);
        Assertions.assertEquals(createdBug.getPriority(), Priority.MAJOR);
    }

    @Test
    public void shouldUpdateBug() {
        bug.setDescription("Bug Description updated");
        bug.setPriority(Priority.CRITICAL);

        Bug updatedBug = bugsService.updateBug(bug);

        assertEquals(updatedBug.getTitle(), "Bug title");
        assertEquals(updatedBug.getDescription(), "Bug Description updated");
        Assertions.assertEquals(updatedBug.getBugStatus(), BugStatus.NEW);
        Assertions.assertEquals(updatedBug.getPriority(), Priority.CRITICAL);
    }

    @Test
    public void shouldDeleteBugById() {
        Bug bug = new Bug();

        bug.setTitle("Bug title");
        bug.setDescription("Bug to be deleted");
        bug.setBugStatus(BugStatus.NEW);
        bug.setPriority(Priority.CRITICAL);

        Bug createdBug = bugsService.createBug(bug);

        assertEquals(createdBug.getTitle(), "Bug title");
        assertEquals(createdBug.getDescription(), "Bug to be deleted");
        Assertions.assertEquals(createdBug.getBugStatus(), BugStatus.NEW);
        Assertions.assertEquals(createdBug.getPriority(), Priority.CRITICAL);

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
