//package com.stylight.controller;
//
//import com.stylight.entity.Bug;
//import com.stylight.entity.Story;
//import com.stylight.model.Plan;
//import com.stylight.model.api.Assignment;
//import com.stylight.service.serviceInterface.PlanningService;
//import com.stylight.service.serviceInterface.StoriesService;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@CrossOrigin
//@RestController
//@RequestMapping(path = "/api/stories")
//@RequiredArgsConstructor
//public class StoriesController {
//
//    Logger logger = LoggerFactory.getLogger(StoriesController.class);
//
//    private final StoriesService storiesService;
//
//    private final PlanningService planningService;
//
//    @GetMapping
//    public ResponseEntity<List<Story>> getStories(@RequestParam(required = false) String title) {
//
//        List<Story> storyList = null;
//
//        logger.info("Getting list of stories");
//        try {
//            storyList = storiesService.getStories(title);
//        } catch (Exception e)
//        {
//            logger.error("Error in getting story list, the error is :" + e.getLocalizedMessage(), e);
//            throw e;
//        }
//
//        return new ResponseEntity<>(storyList, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Story> getStoryById(@PathVariable("id") UUID issueId) {
//
//        Story story = null;
//
//        logger.info("Getting single story for id: " + issueId);
//
//        try {
//            story = storiesService.getStoryById(issueId);
//        } catch (Exception e) {
//            logger.error("Error in getting story for id: " + issueId + ", the error is :" + e.getLocalizedMessage(), e);
//            throw e;
//        }
//
//        return new ResponseEntity<>(story, HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<Story> createStory(@RequestBody Story story) {
//
//        Story storyResponse = null;
//
//        logger.info("Creating a story");
//
//        try {
//            storyResponse = storiesService.createStory(story);
//        } catch (Exception e)
//        {
//            logger.error("Error in creating a story, the error is :" + e.getLocalizedMessage(), e);
//            throw e;
//        }
//
//        return new ResponseEntity<>(storyResponse, HttpStatus.OK);
//    }
//
//    @PatchMapping
//    public ResponseEntity<Story> updateStory(@RequestBody Story story) {
//
//        Story storyResponse = null;
//
//        logger.info("Updating a story");
//
//        try {
//            storyResponse = storiesService.updateStory(story);
//        } catch (Exception e)
//        {
//            logger.error("Error in updating a story, the error is :" + e.getLocalizedMessage(), e);
//            throw e;
//        }
//
//        return new ResponseEntity<>(storyResponse, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteStory(@PathVariable("id") UUID issueId) {
//
//        logger.info("Deleting a story with id: " + issueId);
//
//        try {
//            storiesService.deleteStory(issueId);
//        } catch (Exception e)
//        {
//            logger.error("Error in deleting a story with id: "+ issueId + ", the error is :" + e.getLocalizedMessage(), e);
//            throw e;
//        }
//
//    }
//
//    @PostMapping("/assign")
//    public void assignStory(@RequestBody List<Assignment> assignments) {
//        logger.info("Assigning stories to developers");
//
//        try {
//            storiesService.assign(assignments);
//        } catch (Exception e)
//        {
//            logger.error("Error in assigning stories, the error is :" + e.getLocalizedMessage(), e);
//            throw e;
//        }
//
//    }
//
//    @GetMapping("/plan")
//    public ResponseEntity<List<Plan>> getPlan() {
//        List<Plan> planList = null;
//        logger.info("Getting plan for developers");
//
//        try {
//            planList = planningService.createPlan();
//        } catch (Exception e)
//        {
//            logger.error("Error in getting plan, the error is :" + e.getLocalizedMessage(), e);
//            throw e;
//        }
//
//        return new ResponseEntity<>(planList, HttpStatus.OK);
//    }
//}
