package com.example.SpringBoot.config;

import com.example.SpringBoot.Model.Candlestick;
import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.dao.CandlestickDAO;
import com.example.SpringBoot.dao.InstrumentDAO;
import com.example.SpringBoot.repository.CandlesticksRepository;
import com.example.SpringBoot.service.serviceInterface.CandlesticksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static java.time.Instant.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AggregatorSchedulerTest {

    @Autowired
    AggregatorScheduler aggregatorScheduler;

    @Autowired
    private CandlesticksService candlesticksService;

    @Autowired
    private CandlesticksRepository candlesticksRepository;

    @Test
    public void shouldCleanUpCandlesticksOlderThanOneHour() throws Exception {

        Candlestick candlestick = new Candlestick();

        // Create a candlestick older than 2 hours
        Instant instant = now().minus(2, ChronoUnit.HOURS);
        Date openTime = Date.from(instant);
        Date closeTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));

        candlestick.setIsin("TEST_AI1715314885");
        candlestick.setOpenTimestamp(openTime);
        candlestick.setCloseTimestamp(closeTime);
        candlestick.setOpenPrice(120);
        candlestick.setClosePrice(180);
        candlestick.setLowPrice(100);
        candlestick.setHighPrice(200);

        candlesticksService.saveCandlestick(candlestick);

        // The cleanup should delete candlestick older than an hour
        aggregatorScheduler.candleSticksCleanup();

        List<CandlestickDAO> candlestickDAOS = candlesticksRepository.findRelatedCandlesticksBetweenTimeNative("TEST_AI1715314885", Date.from(now().minus(1, ChronoUnit.HOURS)), Date.from(now()));

        assertTrue(candlestickDAOS.size() == 0);
    }

    @Test
    public void shouldNotCleanUpCandlesticksOlderThanOneHour() throws Exception {

        Candlestick candlestick = new Candlestick();

        // Create a candlestick older than 2 hours
        Instant instant = now().minus(10, ChronoUnit.MINUTES);
        Date openTime = Date.from(instant);
        Date closeTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));

        candlestick.setIsin("TEST_AI1715314885");
        candlestick.setOpenTimestamp(openTime);
        candlestick.setCloseTimestamp(closeTime);
        candlestick.setOpenPrice(120);
        candlestick.setClosePrice(180);
        candlestick.setLowPrice(100);
        candlestick.setHighPrice(200);

        candlesticksService.saveCandlestick(candlestick);

        // The cleanup should delete candlestick older than an hour
        aggregatorScheduler.candleSticksCleanup();

        List<CandlestickDAO> candlestickDAOS = candlesticksRepository.findRelatedCandlesticksBetweenTimeNative("TEST_AI1715314885", Date.from(now().minus(1, ChronoUnit.HOURS)), Date.from(now()));

        assertTrue(candlestickDAOS.size() > 0);
        assertEquals(candlestickDAOS.get(0).getIsin(),"TEST_AI1715314885");
        assertEquals(candlestickDAOS.get(0).getOpenPrice(),120);
        assertEquals(candlestickDAOS.get(0).getClosePrice(),180);
        assertEquals(candlestickDAOS.get(0).getLowPrice(),100);
        assertEquals(candlestickDAOS.get(0).getHighPrice(),200);

        // Delete the candlestick post test
        candlesticksRepository.deleteByIsinNative("TEST_AI1715314885");
    }
}
