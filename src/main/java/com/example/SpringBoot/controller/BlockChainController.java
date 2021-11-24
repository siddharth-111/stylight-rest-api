package com.example.SpringBoot.controller;

import com.example.SpringBoot.Model.BlockChainResponse;
import com.example.SpringBoot.service.serviceInterface.BlockChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class BlockChainController {

    @Autowired
    BlockChainService blockChainService;

    @GetMapping("/blocks/{date}")
    public ResponseEntity<List<BlockChainResponse>> getBitcoinBlocks(@PathVariable("date") String date) {
        try
        {
            return new ResponseEntity<>(blockChainService.getBitcoinBlocks(date), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
