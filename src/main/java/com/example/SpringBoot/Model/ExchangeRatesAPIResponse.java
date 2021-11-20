package com.example.SpringBoot.Model;

import java.util.LinkedHashMap;

public class ExchangeRatesAPIResponse {
    public LinkedHashMap<String, Double> rates;
    public long timestamp;
    public boolean historical;
    public String base;
    public String date;

    public LinkedHashMap<String, Double> getRates() {
        return rates;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setRates(LinkedHashMap<String, Double> rates)
    {
        this.rates = rates;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isHistorical() {


        return historical;
    }

    public void setHistorical(boolean historical) {
        this.historical = historical;
    }
}
