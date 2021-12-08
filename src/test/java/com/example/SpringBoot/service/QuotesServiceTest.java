package com.example.SpringBoot.service;

import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.Model.Quote;
import com.example.SpringBoot.dao.InstrumentDAO;
import com.example.SpringBoot.dao.QuoteDAO;
import com.example.SpringBoot.repository.InstrumentsRepository;
import com.example.SpringBoot.repository.QuotesRepository;
import com.example.SpringBoot.service.serviceInterface.InstrumentsService;
import com.example.SpringBoot.service.serviceInterface.QuotesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class QuotesServiceTest {

    @Autowired
    private QuotesService quotesService;

    @Autowired
    private QuotesRepository quotesRepository;

    @Test
    public void shouldSaveQuote() throws Exception {
        Quote quote = new Quote();
        quote.setIsin("TEST_AI1715314885");
        quote.setPrice(100);

        quotesService.saveQuote(quote);

        List<QuoteDAO> quotesResponse = quotesRepository.findQuotesByIsinNative("TEST_AI1715314885");

        assertTrue(quotesResponse.size() > 0);
        assertEquals(quotesResponse.get(0).getPrice(), 100);
        assertEquals(quotesResponse.get(0).getIsin(), "TEST_AI1715314885");

        quotesService.deleteQuotesByIsinBeforeCreationTime("TEST_AI1715314885", Date.from(Instant.now()));
    }

    @Test
    public void shouldDeleteQuote() throws Exception {
        Quote quote = new Quote();
        quote.setIsin("TEST_AI1715314885");
        quote.setPrice(100);

        quotesService.saveQuote(quote);

        List<QuoteDAO> quotesResponse = quotesRepository.findQuotesByIsinNative("TEST_AI1715314885");
        assertTrue(quotesResponse.size() > 0);

        quotesService.deleteQuotesByIsinBeforeCreationTime("TEST_AI1715314885", Date.from(Instant.now()));

        quotesResponse = quotesRepository.findQuotesByIsinNative("TEST_AI1715314885");
        assertTrue(quotesResponse.size() == 0);

    }
}
