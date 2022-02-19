//package com.ratepay.challenge.service;
//
//import com.ratepay.challenge.model.Quote;
//import com.ratepay.challenge.entity.QuoteDAO;
//import com.ratepay.challenge.repository.QuotesRepository;
//import com.ratepay.challenge.service.serviceInterface.QuotesService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//public class QuotesServiceTest {
//
//    @Autowired
//    private QuotesService quotesService;
//
//    @Autowired
//    private QuotesRepository quotesRepository;
//
//    @Test
//    public void shouldSaveQuote() throws Exception {
//        Quote quote = new Quote();
//        quote.setIsin("TEST_AI1715314885");
//        quote.setPrice(100);
//
//        quotesService.saveQuote(quote);
//
//        List<QuoteDAO> quotesResponse = quotesRepository.findQuotesByIsinNative("TEST_AI1715314885");
//
//        assertTrue(quotesResponse.size() > 0);
//        assertEquals(quotesResponse.get(0).getPrice(), 100);
//        assertEquals(quotesResponse.get(0).getIsin(), "TEST_AI1715314885");
//
//        quotesService.deleteQuotesByIsinBeforeCreationTime("TEST_AI1715314885", Date.from(Instant.now()));
//    }
//
//    @Test
//    public void shouldDeleteQuote() throws Exception {
//        Quote quote = new Quote();
//        quote.setIsin("TEST_AI1715314885");
//        quote.setPrice(100);
//
//        quotesService.saveQuote(quote);
//
//        List<QuoteDAO> quotesResponse = quotesRepository.findQuotesByIsinNative("TEST_AI1715314885");
//        assertTrue(quotesResponse.size() > 0);
//
//        quotesService.deleteQuotesByIsinBeforeCreationTime("TEST_AI1715314885", Date.from(Instant.now()));
//
//        quotesResponse = quotesRepository.findQuotesByIsinNative("TEST_AI1715314885");
//        assertTrue(quotesResponse.size() == 0);
//
//    }
//
//    @Test
//    public void shouldFindQuoteBetweenTimestamp() throws Exception {
//        Quote quote = new Quote();
//        quote.setIsin("TEST_AI1715314885");
//        quote.setPrice(100);
//
//        // Create a quote
//        quotesService.saveQuote(quote);
//
//        // Find the created quote between previous minute and now
//        List<QuoteDAO> quotesResponse = quotesRepository.findRelatedQuotesBetweenTimeNative("TEST_AI1715314885", Date.from(Instant.now().minus(1, ChronoUnit.MINUTES)), Date.from(Instant.now()));
//
//        assertTrue(quotesResponse.size() > 0);
//        assertEquals(quotesResponse.get(0).getPrice(), 100);
//        assertEquals(quotesResponse.get(0).getIsin(), "TEST_AI1715314885");
//
//        quotesService.deleteQuotesByIsinBeforeCreationTime("TEST_AI1715314885", Date.from(Instant.now()));
//    }
//
//    @Test
//    public void shouldNotFindQuoteBetweenTimestamp() throws Exception {
//        Quote quote = new Quote();
//        quote.setIsin("TEST_AI1715314885");
//        quote.setPrice(100);
//
//        // Create a quote
//        quotesService.saveQuote(quote);
//
//        // Try to find quote for an older timestamp
//        List<QuoteDAO> quotesResponse = quotesRepository.findRelatedQuotesBetweenTimeNative("TEST_AI1715314885", Date.from(Instant.now().minus(2, ChronoUnit.MINUTES)), Date.from(Instant.now().minus(1, ChronoUnit.MINUTES)));
//
//        assertTrue(quotesResponse.size() == 0);
//
//        quotesService.deleteQuotesByIsinBeforeCreationTime("TEST_AI1715314885", Date.from(Instant.now()));
//    }
//}
