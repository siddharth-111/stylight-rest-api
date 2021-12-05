package com.example.SpringBoot.service.serviceInterface;

import com.example.SpringBoot.Model.Quote;

import java.util.Date;

public interface QuotesService {
    public abstract void saveQuote(Quote quote) throws Exception;
    void deleteQuotesBeforeTime(Date closeTime) throws Exception;
}
