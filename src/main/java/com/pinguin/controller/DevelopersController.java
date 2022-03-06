package com.pinguin.controller;

import com.pinguin.entity.Developer;
import com.pinguin.entity.Story;
import com.pinguin.service.serviceInterface.DevelopersService;
import com.pinguin.service.serviceInterface.StoriesService;
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
@RequestMapping(path = "/api/developers")
@RequiredArgsConstructor
public class DevelopersController {

    Logger logger = LoggerFactory.getLogger(DevelopersController.class);

    private final DevelopersService developersService;

    @GetMapping
    public ResponseEntity<List<Developer>> getDevelopers(@RequestParam(required = false) String name) {
        List<Developer> developerList  = null;

        logger.info("Getting list of developers");
        try {
            developerList = developersService.getDevelopers(name);
        } catch (Exception e)
        {
            logger.error("Error in getting developer list, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(developerList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable("id") UUID developerId) {
        Developer developer = null;

        logger.info("Getting single developer by id: " + developerId);

        try {
            developer = developersService.getDeveloperById(developerId);
        } catch (Exception e) {
            logger.error("Error in getting developer by id: " + developerId + ", the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(developer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer developer) {
        Developer developerResponse = null;

        logger.info("Creating a developer");

        try {
            developerResponse = developersService.createDeveloper(developer);
        } catch (Exception e)
        {
            logger.error("Error in creating a developer, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(developerResponse, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Developer> updateDeveloper(@RequestBody Developer developer) {
        Developer developerResponse = null;

        logger.info("Updating a developer");

        try {
            developerResponse = developersService.updateDeveloper(developer);
        } catch (Exception e)
        {
            logger.error("Error in updating a developer, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(developerResponse, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public void deleteDeveloper(@PathVariable("id") UUID developerId) {
        logger.info("Deleting a developer with id: " + developerId);

        try {
            developersService.deleteDeveloper(developerId);
        } catch (Exception e)
        {
            logger.error("Error in deleting a developer with id: "+ developerId + ", the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

    }

}
