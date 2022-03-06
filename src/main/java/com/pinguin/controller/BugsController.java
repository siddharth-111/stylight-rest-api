package com.pinguin.controller;

import com.pinguin.entity.Bug;
import com.pinguin.model.api.Assignment;
import com.pinguin.service.serviceInterface.BugsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/bugs")
@RequiredArgsConstructor
public class BugsController {

    Logger logger = LoggerFactory.getLogger(BugsController.class);

    private final BugsService bugsService;

    @GetMapping
    public ResponseEntity<List<Bug>> getBugs(@RequestParam(required = false) String title) {
        List<Bug> bugList = null;

        logger.info("Getting list of bugs");
        try {
            bugList = bugsService.getBugs(title);
        } catch (Exception e)
        {
            logger.error("Error in getting bugs list, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(bugList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bug> getBug(@PathVariable("id") UUID issueId) {
        Bug bug = null;

        logger.info("Getting single bug for id: " + issueId);
        try {
            bug = bugsService.getBugById(issueId);
        } catch (Exception e) {
            logger.error("Error in getting bug for id: " + issueId + ", the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(bug, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Bug> createBug(@RequestBody Bug bug) {
        Bug bugServiceResponse = null;

        logger.info("Creating a bug");

        try {
            bugServiceResponse = bugsService.createBug(bug);
        } catch (Exception e)
        {
            logger.error("Error in creating a bug, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(bugServiceResponse, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Bug> updateBug(@RequestBody Bug bug) {
        Bug bugServiceResponse = null;

        logger.info("Updating a bug");
        try {
            bugServiceResponse = bugsService.updateBug(bug);
        } catch (Exception e)
        {
            logger.error("Error in updating a bug, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(bugServiceResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteBug(@PathVariable("id") UUID issueId) {

        logger.info("Deleting a bug with id: " + issueId);

        try {
            bugsService.deleteBug(issueId);
        } catch (Exception e)
        {
            logger.error("Error in deleting a bug with id: "+ issueId + ", the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

    }

    @PostMapping("/assign")
    public void assignBugs(@RequestBody List<Assignment> assignments) {

        logger.info("Assigning bugs to developers");

        try {
            bugsService.assign(assignments);
        } catch (Exception e)
        {
            logger.error("Error in assigning bugs, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

    }
}
