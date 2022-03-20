package com.stylight.controller;

import com.stylight.entity.Url;
import com.stylight.service.serviceInterface.UrlService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping(path = "/api/urls/")
@RequiredArgsConstructor
public class UrlsController {

    Logger logger = LoggerFactory.getLogger(UrlsController.class);

    private final UrlService urlService;

    @GetMapping("/prettyUrl")
    public ResponseEntity<List<Url>> getUrls(@RequestBody List<String> orderedParameters) throws Exception {
        List<Url> urlList = null;

        logger.info("Getting list of bugs");
        try {
            urlList = urlService.getPrettyUrl(orderedParameters);
        } catch (Exception e)
        {
            logger.error("Error in getting bugs list, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(urlList, HttpStatus.OK);
    }

    @GetMapping("/orderedParameters")
    public ResponseEntity<List<Url>> getOrderedParameters(@RequestBody List<String> prettyUrls) throws Exception {
        List<Url> urlList = null;

        logger.info("Getting list of bugs");
        try {
            urlList = urlService.getOrderedParameters(prettyUrls);
        } catch (Exception e)
        {
            logger.error("Error in getting bugs list, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(urlList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Url> createMapping(@RequestBody Url url) {
        Url urlServiceResponse = null;

        logger.info("Creating a url");

        try {
            urlServiceResponse = urlService.createMapping(url);
        } catch (Exception e)
        {
            logger.error("Error in creating a bug, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(urlServiceResponse, HttpStatus.OK);
    }

//    @PatchMapping
//    public ResponseEntity<Bug> updateBug(@RequestBody Bug bug) {
//        Bug bugServiceResponse = null;
//
//        logger.info("Updating a bug");
//        try {
//            bugServiceResponse = bugsService.updateBug(bug);
//        } catch (Exception e)
//        {
//            logger.error("Error in updating a bug, the error is :" + e.getLocalizedMessage(), e);
//            throw e;
//        }
//
//        return new ResponseEntity<>(bugServiceResponse, HttpStatus.OK);
//    }

//    @DeleteMapping("/{id}")
//    public void deleteBug(@PathVariable("id") UUID issueId) {
//
//        logger.info("Deleting a bug with id: " + issueId);
//
//        try {
//            bugsService.deleteBug(issueId);
//        } catch (Exception e)
//        {
//            logger.error("Error in deleting a bug with id: "+ issueId + ", the error is :" + e.getLocalizedMessage(), e);
//            throw e;
//        }
//
//    }
//
//    @PostMapping("/assign")
//    public void assignBugs(@RequestBody List<Assignment> assignments) {
//
//        logger.info("Assigning bugs to developers");
//
//        try {
//            bugsService.assign(assignments);
//        } catch (Exception e)
//        {
//            logger.error("Error in assigning bugs, the error is :" + e.getLocalizedMessage(), e);
//            throw e;
//        }
//
//    }
}
