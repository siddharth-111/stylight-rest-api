package com.example.SpringBoot.service;

import com.example.SpringBoot.Model.Candlestick;
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
public class CandlesticksServiceTest {

    @Autowired
    CandlesticksService candlesticksService;

    @Autowired
    private CandlesticksRepository candlesticksRepository;

    @Test
    public void shouldSaveCandlestick() throws Exception
    {
        Candlestick candlestick = new Candlestick();

        // Create a candlestick older than 2 hours
        Instant instant = now().minus(2, ChronoUnit.MINUTES);
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

        List<Candlestick> candlestickList = candlesticksService.findCandlesticks("TEST_AI1715314885");

        assertTrue(candlestickList.size() > 0);
        assertEquals(candlestickList.get(0).getIsin(),"TEST_AI1715314885");
        assertEquals(candlestickList.get(0).getOpenPrice(),120);
        assertEquals(candlestickList.get(0).getClosePrice(),180);
        assertEquals(candlestickList.get(0).getLowPrice(),100);
        assertEquals(candlestickList.get(0).getHighPrice(),200);

        // Delete the candlestick post test
        candlesticksRepository.deleteByIsinNative("TEST_AI1715314885");
    }

    @Test
    public void shouldReturnCandlesWithSinglePrice() throws Exception {
        Candlestick candlestick = new Candlestick();

        Instant instant = now().truncatedTo(ChronoUnit.MINUTES).minus(2, ChronoUnit.MINUTES);
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

        List<Candlestick> candlestickList = candlesticksService.findCandlesticks("TEST_AI1715314885");
        assertTrue(candlestickList.size() == 30);
        assertEquals(candlestickList.get(0).getIsin(),"TEST_AI1715314885");
        assertEquals(candlestickList.get(0).getOpenPrice(),120);
        assertEquals(candlestickList.get(0).getClosePrice(),180);
        assertEquals(candlestickList.get(0).getLowPrice(),100);
        assertEquals(candlestickList.get(0).getHighPrice(),200);

        assertEquals(candlestickList.get(1).getIsin(),"TEST_AI1715314885");
        assertEquals(candlestickList.get(1).getOpenPrice(),120);
        assertEquals(candlestickList.get(1).getClosePrice(),180);
        assertEquals(candlestickList.get(1).getLowPrice(),100);
        assertEquals(candlestickList.get(1).getHighPrice(),200);

        // Delete the candlestick post test
        candlesticksRepository.deleteByIsinNative("TEST_AI1715314885");

    }

    @Test
    public void shouldReturnCandlesWithMultiplePrices() throws Exception {
        Candlestick candlestick = new Candlestick();

        Instant instant = now().truncatedTo(ChronoUnit.MINUTES).minus(30, ChronoUnit.MINUTES);
        Date openTime = Date.from(instant);
        Date closeTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));

        candlestick.setIsin("TEST_AI1715314885");
        candlestick.setOpenTimestamp(openTime);
        candlestick.setCloseTimestamp(closeTime);
        candlestick.setOpenPrice(120);
        candlestick.setClosePrice(180);
        candlestick.setLowPrice(100);
        candlestick.setHighPrice(200);

        // Save candlestick which is 30 minutes old
        candlesticksService.saveCandlestick(candlestick);

        instant = now().truncatedTo(ChronoUnit.MINUTES).minus(29, ChronoUnit.MINUTES);
        openTime = Date.from(instant);
        closeTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));

        candlestick.setIsin("TEST_AI1715314885");
        candlestick.setOpenTimestamp(openTime);
        candlestick.setCloseTimestamp(closeTime);
        candlestick.setOpenPrice(105);
        candlestick.setClosePrice(155);
        candlestick.setLowPrice(110);
        candlestick.setHighPrice(250);

        // Save candlestick which is 29 minutes old
        candlesticksService.saveCandlestick(candlestick);

        List<Candlestick> candlestickList = candlesticksService.findCandlesticks("TEST_AI1715314885");
        assertTrue(candlestickList.size() == 30);
        assertEquals(candlestickList.get(0).getIsin(),"TEST_AI1715314885");
        assertEquals(candlestickList.get(0).getOpenPrice(),120);
        assertEquals(candlestickList.get(0).getClosePrice(),180);
        assertEquals(candlestickList.get(0).getLowPrice(),100);
        assertEquals(candlestickList.get(0).getHighPrice(),200);

        assertEquals(candlestickList.get(1).getIsin(),"TEST_AI1715314885");
        assertEquals(candlestickList.get(1).getOpenPrice(),105);
        assertEquals(candlestickList.get(1).getClosePrice(),155);
        assertEquals(candlestickList.get(1).getLowPrice(),110);
        assertEquals(candlestickList.get(1).getHighPrice(),250);

        // Delete the candlestick post test
        candlesticksRepository.deleteByIsinNative("TEST_AI1715314885");
    }

    @Test
    public void shouldReturnCandlesFillingIntermediatePrices() throws Exception {
        Candlestick candlestick = new Candlestick();

        Instant instant = now().truncatedTo(ChronoUnit.MINUTES).minus(30, ChronoUnit.MINUTES);
        Date openTime = Date.from(instant);
        Date closeTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));

        candlestick.setIsin("TEST_AI1715314885");
        candlestick.setOpenTimestamp(openTime);
        candlestick.setCloseTimestamp(closeTime);
        candlestick.setOpenPrice(120);
        candlestick.setClosePrice(180);
        candlestick.setLowPrice(100);
        candlestick.setHighPrice(200);

        // Save candlestick which is 30 minutes old
        candlesticksService.saveCandlestick(candlestick);

        instant = now().truncatedTo(ChronoUnit.MINUTES).minus(28, ChronoUnit.MINUTES);
        openTime = Date.from(instant);
        closeTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));

        candlestick.setIsin("TEST_AI1715314885");
        candlestick.setOpenTimestamp(openTime);
        candlestick.setCloseTimestamp(closeTime);
        candlestick.setOpenPrice(105);
        candlestick.setClosePrice(155);
        candlestick.setLowPrice(110);
        candlestick.setHighPrice(250);

        // Save candlestick which is 28 minutes old
        candlesticksService.saveCandlestick(candlestick);

        List<Candlestick> candlestickList = candlesticksService.findCandlesticks("TEST_AI1715314885");
        assertTrue(candlestickList.size() == 30);
        assertEquals(candlestickList.get(0).getIsin(),"TEST_AI1715314885");
        assertEquals(candlestickList.get(0).getOpenPrice(),120);
        assertEquals(candlestickList.get(0).getClosePrice(),180);
        assertEquals(candlestickList.get(0).getLowPrice(),100);
        assertEquals(candlestickList.get(0).getHighPrice(),200);

        assertEquals(candlestickList.get(1).getIsin(),"TEST_AI1715314885");
        assertEquals(candlestickList.get(1).getOpenPrice(),120);
        assertEquals(candlestickList.get(1).getClosePrice(),180);
        assertEquals(candlestickList.get(1).getLowPrice(),100);
        assertEquals(candlestickList.get(1).getHighPrice(),200);

        assertEquals(candlestickList.get(2).getIsin(),"TEST_AI1715314885");
        assertEquals(candlestickList.get(2).getOpenPrice(),105);
        assertEquals(candlestickList.get(2).getClosePrice(),155);
        assertEquals(candlestickList.get(2).getLowPrice(),110);
        assertEquals(candlestickList.get(2).getHighPrice(),250);

        // Delete the candlestick post test
        candlesticksRepository.deleteByIsinNative("TEST_AI1715314885");
    }

}
