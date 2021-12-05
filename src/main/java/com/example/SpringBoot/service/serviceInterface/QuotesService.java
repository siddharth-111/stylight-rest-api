package com.example.SpringBoot.service.serviceInterface;

import com.example.SpringBoot.Model.Quote;

public interface QuotesService {
    public abstract void saveQuote(Quote quote) throws Exception;
}
