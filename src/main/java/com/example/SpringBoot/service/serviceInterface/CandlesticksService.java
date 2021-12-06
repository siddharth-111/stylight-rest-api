package com.example.SpringBoot.service.serviceInterface;

import com.example.SpringBoot.Model.Candlestick;

import java.util.List;

public interface CandlesticksService {
    void saveCandlestick(Candlestick candlestick) throws Exception;
    List<Candlestick> findCandlesticks(String isin) throws Exception;
}
