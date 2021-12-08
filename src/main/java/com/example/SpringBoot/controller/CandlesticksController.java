package com.example.SpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SpringBoot.Model.Candlestick;
import com.example.SpringBoot.service.serviceInterface.CandlesticksService;


import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/")
public class CandlesticksController {

    @Autowired
    CandlesticksService candlesticksService;


    @GetMapping("/candlesticks")
    public ResponseEntity<List<Candlestick>> getCandleSticks(@RequestParam(required = true) String isin) {
        try
        {
            List<Candlestick> candlesticks = candlesticksService.findCandlesticks(isin);
            return new ResponseEntity<>(candlesticks, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
