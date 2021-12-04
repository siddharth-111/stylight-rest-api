package com.example.SpringBoot.Model;

import com.example.SpringBoot.Model.enums.Trends;

import java.util.LinkedHashMap;

public class ExchangeRateResponse {

    public LinkedHashMap<String, Double> rates;
    public LinkedHashMap<String, Double> averageRates;
    public Trends trends;

    public void setRates(LinkedHashMap<String, Double> rates)
    {
        this.rates = rates;
    }


    public LinkedHashMap<String, Double> getRates()
    {
        return rates;
    }

    public void setAverageRates(LinkedHashMap<String, Double> averageRates)
    {
        this.averageRates = averageRates;
    }

    public LinkedHashMap<String, Double> getAverageRates()
    {
        return averageRates;
    }

    public void setTrends(Trends trends)
    {
        this.trends = trends;
    }

    public Trends getTrends()
    {
        return trends;
    }
}
