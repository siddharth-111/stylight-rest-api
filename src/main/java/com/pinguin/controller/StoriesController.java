package com.pinguin.controller;

import com.pinguin.entity.Story;
import com.pinguin.service.serviceInterface.StoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/stories")
@RequiredArgsConstructor
public class StoriesController {

    private final StoriesService storiesService;

    @GetMapping
    public ResponseEntity<List<Story>> storiesService(@RequestParam(required = false) String title) {
        List<Story> storyList = storiesService.getStories();
        return new ResponseEntity<>(storyList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Story> getStoryById(@PathVariable("id") UUID issueId) {

        Story story = storiesService.getStoryById(issueId);

        return new ResponseEntity<>(story, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Story> createStory(@RequestBody Story story) {
        Story storyResponse = storiesService.createStory(story);
        return new ResponseEntity<>(storyResponse, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Story> updateStory(@RequestBody Story story) {

        Story storyResponse = storiesService.updateStory(story);

        return new ResponseEntity<>(storyResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteStory(@PathVariable("id") UUID issueId) {
        storiesService.deleteStory(issueId);
    }
}
