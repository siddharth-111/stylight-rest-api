package com.pinguin.controller;

import com.pinguin.entity.Bug;
import com.pinguin.service.serviceInterface.BugsService;
import lombok.RequiredArgsConstructor;
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

    private final BugsService bugsService;

    @GetMapping
    public ResponseEntity<List<Bug>> getBugs(@RequestParam(required = false) String title) {
        List<Bug> bugList = bugsService.getBugs(title);
        return new ResponseEntity<>(bugList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bug> getBug(@PathVariable("id") UUID issueId) {

        Bug bug = bugsService.getBugById(issueId);

        return new ResponseEntity<>(bug, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Bug> createBug(@RequestBody Bug bug) {
        Bug bugServiceResponse = bugsService.createBug(bug);
        return new ResponseEntity<>(bugServiceResponse, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Bug> updateBug(@RequestBody Bug bug) {

        Bug bugServiceResponse = bugsService.updateBug(bug);

        return new ResponseEntity<>(bugServiceResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteBug(@PathVariable("id") UUID issueId) {
        bugsService.deleteBug(issueId);
    }
}
