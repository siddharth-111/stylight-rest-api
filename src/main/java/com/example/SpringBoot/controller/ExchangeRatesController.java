package com.example.SpringBoot.controller;

import com.example.SpringBoot.Model.ExchangeRateResponse;
import com.example.SpringBoot.service.serviceInterface.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ExchangeRatesController {

    @Autowired
    ExchangeRatesService exchangeRatesService;

    @GetMapping("/exchange-rate/{date}/{baseCurrency}/{targetCurrency}")
    public ResponseEntity<ExchangeRateResponse> getExchangeRate(@PathVariable("date") String date, @PathVariable("baseCurrency") String baseCurrency,
                                                                @PathVariable("targetCurrency") String targetCurrency) {
        try
        {
            return new ResponseEntity<>(exchangeRatesService.getExchangeRates(date, baseCurrency, targetCurrency), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exchange-rate/history/daily/{yyyy}/{mm}/{dd}")
    public ResponseEntity<List<ExchangeRateResponse>> getExchangeRateForDate(@PathVariable("yyyy") String year, @PathVariable("mm") String month,
                                                                             @PathVariable("dd") String day){
        try
        {
            return new ResponseEntity<>(exchangeRatesService.getExchangeRateForDate(year, month, day), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exchange-rate/history/monthly/{yyyy}/{mm}")
    public ResponseEntity<List<ExchangeRateResponse>> getExchangeRateForMonth(@PathVariable("yyyy") String year, @PathVariable("mm") String month){
        try
        {
            return new ResponseEntity<>(exchangeRatesService.getExchangeRateForMonth(Integer.parseInt(year), Integer.parseInt(month)), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
