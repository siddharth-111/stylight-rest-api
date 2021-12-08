package com.example.SpringBoot.service.serviceInterface;

import com.example.SpringBoot.model.Candlestick;

import java.util.Date;
import java.util.List;

public interface CandlesticksService {
    void saveCandlestick(Candlestick candlestick) throws Exception;
    void deleteCandlesticksBeforeTime(Date closeTime) throws Exception;
    void deleteCandlesticksByIsinBeforeCreationTime(String isin, Date creationDate) throws Exception;
    List<Candlestick> findCandlesticks(String isin) throws Exception;
}
