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
@RequestMapping(path = "/api/urls")
@RequiredArgsConstructor
public class UrlsController {

    Logger logger = LoggerFactory.getLogger(UrlsController.class);

    private final UrlService urlService;

    @GetMapping("/prettyUrl")
    public ResponseEntity<List<Url>> getUrls(@RequestBody List<String> orderedParameters) throws Exception {
        List<Url> urlList = null;

        logger.info("Getting list of pretty urls");
        try {
            urlList = urlService.getPrettyUrl(orderedParameters);
        } catch (Exception e)
        {
            logger.error("Error in getting list of pretty urls, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(urlList, HttpStatus.OK);
    }

    @GetMapping("/orderedParameters")
    public ResponseEntity<List<Url>> getOrderedParameters(@RequestBody List<String> prettyUrls) throws Exception {
        List<Url> urlList = null;

        logger.info("Getting list of ordered parameters");
        try {
            urlList = urlService.getOrderedParameters(prettyUrls);
        } catch (Exception e)
        {
            logger.error("Error in getting list of ordered parameters, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(urlList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Url> createMapping(@RequestBody Url url) {
        Url urlServiceResponse = null;

        logger.info("Creating a mapping url");

        try {
            urlServiceResponse = urlService.createMapping(url);
        } catch (Exception e)
        {
            logger.error("Error in creating a mapping url, the error is :" + e.getLocalizedMessage(), e);
            throw e;
        }

        return new ResponseEntity<>(urlServiceResponse, HttpStatus.OK);
    }
}
