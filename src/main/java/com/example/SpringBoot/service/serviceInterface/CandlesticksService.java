package com.example.SpringBoot.service.serviceInterface;

import com.example.SpringBoot.Model.Candlestick;

public interface CandlesticksService {
    void saveCandlestick(Candlestick candlestick) throws Exception;
}
