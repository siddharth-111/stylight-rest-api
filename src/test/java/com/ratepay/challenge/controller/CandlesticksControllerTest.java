//package com.ratepay.challenge.controller;
//
//
//import com.ratepay.challenge.model.Candlestick;
//import com.ratepay.challenge.repository.CandlesticksRepository;
//import com.ratepay.challenge.service.serviceInterface.CandlesticksService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//
//import java.util.Date;
//
//import static java.time.Instant.now;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CandlesticksControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private CandlesticksService candlesticksService;
//
//    @Autowired
//    private CandlesticksRepository candlesticksRepository;
//
//    @Test
//    public void shouldNotReturnCandles() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .get("/candlesticks")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .queryParam("isin", ""))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(MockMvcResultMatchers.content().string("[]"));
//    }
//
//    @Test
//    public void shouldReturnCandlesWithSinglePrice() throws Exception {
//        Candlestick candlestick = new Candlestick();
//
//        Instant instant = now().truncatedTo(ChronoUnit.MINUTES).minus(2, ChronoUnit.MINUTES);
//        Date openTime = Date.from(instant);
//        Date closeTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));
//
//        candlestick.setIsin("TEST_AI1715314885");
//        candlestick.setOpenTimestamp(openTime);
//        candlestick.setCloseTimestamp(closeTime);
//        candlestick.setOpenPrice(120);
//        candlestick.setClosePrice(180);
//        candlestick.setLowPrice(100);
//        candlestick.setHighPrice(200);
//
//        // Save only a single candlestick, hence all the values will be same within last 30 minutes
//        candlesticksService.saveCandlestick(candlestick);
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .get("/candlesticks")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .queryParam("isin", "TEST_AI1715314885"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].openPrice").value(120))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].closePrice").value(180))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].highPrice").value(200))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lowPrice").value(100))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].openPrice").value(120))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].closePrice").value(180))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].highPrice").value(200))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lowPrice").value(100));
//
//        // Delete the candlestick post test
//        candlesticksRepository.deleteByIsinNative("TEST_AI1715314885");
//    }
//
//    @Test
//    public void shouldReturnCandlesWithMultiplePrices() throws Exception {
//        Candlestick candlestick = new Candlestick();
//
//        Instant instant = now().truncatedTo(ChronoUnit.MINUTES).minus(30, ChronoUnit.MINUTES);
//        Date openTime = Date.from(instant);
//        Date closeTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));
//
//        candlestick.setIsin("TEST_AI1715314885");
//        candlestick.setOpenTimestamp(openTime);
//        candlestick.setCloseTimestamp(closeTime);
//        candlestick.setOpenPrice(120);
//        candlestick.setClosePrice(180);
//        candlestick.setLowPrice(100);
//        candlestick.setHighPrice(200);
//
//        // Save candlestick which is 30 minutes old
//        candlesticksService.saveCandlestick(candlestick);
//
//        instant = now().truncatedTo(ChronoUnit.MINUTES).minus(29, ChronoUnit.MINUTES);
//        openTime = Date.from(instant);
//        closeTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));
//
//        candlestick.setIsin("TEST_AI1715314885");
//        candlestick.setOpenTimestamp(openTime);
//        candlestick.setCloseTimestamp(closeTime);
//        candlestick.setOpenPrice(105);
//        candlestick.setClosePrice(155);
//        candlestick.setLowPrice(110);
//        candlestick.setHighPrice(250);
//
//        // Save candlestick which is 29 minutes old
//        candlesticksService.saveCandlestick(candlestick);
//
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .get("/candlesticks")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .queryParam("isin", "TEST_AI1715314885"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].openPrice").value(120))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].closePrice").value(180))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].highPrice").value(200))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lowPrice").value(100))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].openPrice").value(105))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].closePrice").value(155))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lowPrice").value(110))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].highPrice").value(250));
//
//        // Delete the candlestick post test
//        candlesticksRepository.deleteByIsinNative("TEST_AI1715314885");
//    }
//
//    @Test
//    public void shouldReturnCandlesFillingIntermediatePrices() throws Exception {
//        Candlestick candlestick = new Candlestick();
//
//        Instant instant = now().truncatedTo(ChronoUnit.MINUTES).minus(30, ChronoUnit.MINUTES);
//        Date openTime = Date.from(instant);
//        Date closeTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));
//
//        candlestick.setIsin("TEST_AI1715314885");
//        candlestick.setOpenTimestamp(openTime);
//        candlestick.setCloseTimestamp(closeTime);
//        candlestick.setOpenPrice(120);
//        candlestick.setClosePrice(180);
//        candlestick.setLowPrice(100);
//        candlestick.setHighPrice(200);
//
//        // Save candlestick which is 30 minutes old
//        candlesticksService.saveCandlestick(candlestick);
//
//        instant = now().truncatedTo(ChronoUnit.MINUTES).minus(28, ChronoUnit.MINUTES);
//        openTime = Date.from(instant);
//        closeTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));
//
//        candlestick.setIsin("TEST_AI1715314885");
//        candlestick.setOpenTimestamp(openTime);
//        candlestick.setCloseTimestamp(closeTime);
//        candlestick.setOpenPrice(105);
//        candlestick.setClosePrice(155);
//        candlestick.setLowPrice(110);
//        candlestick.setHighPrice(250);
//
//        // Save candlestick which is 28 minutes old
//        candlesticksService.saveCandlestick(candlestick);
//
//        // Assert that the candle which is 29 minutes old has values from candle which is 30 minutes old
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .get("/candlesticks")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .queryParam("isin", "TEST_AI1715314885"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].openPrice").value(120))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].closePrice").value(180))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].highPrice").value(200))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lowPrice").value(100))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].openPrice").value(120))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].closePrice").value(180))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].highPrice").value(200))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lowPrice").value(100))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[2].openPrice").value(105))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[2].closePrice").value(155))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[2].lowPrice").value(110))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[2].highPrice").value(250));
//
//        // Delete the candlestick post test
//        candlesticksRepository.deleteByIsinNative("TEST_AI1715314885");
//    }
//}
