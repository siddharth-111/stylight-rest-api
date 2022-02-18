package com.ratepay.challenge.model.api;

public class QuotesDataResponse {
    private double price;
    private String isin;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }
}
