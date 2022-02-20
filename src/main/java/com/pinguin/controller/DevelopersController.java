package com.pinguin.controller;

import com.pinguin.entity.Developer;
import com.pinguin.entity.Story;
import com.pinguin.service.serviceInterface.DevelopersService;
import com.pinguin.service.serviceInterface.StoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/developers")
@RequiredArgsConstructor
public class DevelopersController {

    private final DevelopersService developersService;

    @GetMapping
    public ResponseEntity<List<Developer>> getDevelopers(@RequestParam(required = false) String title) {
        List<Developer> storyList = developersService.getDevelopers();
        return new ResponseEntity<>(storyList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable("id") UUID developerId) {

        Developer developer = developersService.getDeveloperById(developerId);

        return new ResponseEntity<>(developer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer developer) {
        Developer developerResponse = developersService.createDeveloper(developer);
        return new ResponseEntity<>(developerResponse, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Developer> updateDeveloper(@RequestBody Developer developer) {

        Developer developerResponse = developersService.updateDeveloper(developer);

        return new ResponseEntity<>(developerResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteStory(@PathVariable("id") UUID developerId) {
        developersService.deleteDeveloper(developerId);
    }

}
