package com.example.SpringBoot.service.serviceInterface;

import com.example.SpringBoot.Model.Quote;

import java.util.Date;
import java.util.List;

public interface QuotesService {
    void saveQuote(Quote quote) throws Exception;
    void deleteQuotesBeforeTime(Date closeTime) throws Exception;
    List<Quote> findRelatedQuotesBetweenTime(String isin, Date openTime, Date closeTime) throws Exception;
    void deleteQuotesByIsinBeforeCreationTime(String isin, Date creationDate) throws Exception;
}
