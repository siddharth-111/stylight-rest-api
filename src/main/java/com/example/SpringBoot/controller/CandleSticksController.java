package com.example.SpringBoot.controller;

import com.example.SpringBoot.Model.BlockChainResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/")
public class CandleSticksController {

    @GetMapping("/candlesticks")
    public ResponseEntity<String> getCandleSticks(@RequestParam(required = true) String isin) {
        try
        {
            return new ResponseEntity<>(isin, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
