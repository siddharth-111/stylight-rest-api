package com.example.SpringBoot.service.serviceInterface;

import com.example.SpringBoot.Model.ExchangeRateResponse;

import java.util.List;

public interface ExchangeRatesService {
    public abstract ExchangeRateResponse getExchangeRates(String date, String baseCurrency, String targetCurrency) throws Exception;
    public abstract List<ExchangeRateResponse> getExchangeRateForDate(String year, String month, String day) throws Exception;
    public abstract List<ExchangeRateResponse> getExchangeRateForMonth(int year, int month) throws Exception;
}
