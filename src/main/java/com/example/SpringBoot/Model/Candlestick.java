package com.example.SpringBoot.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Candlestick {
    private Date openTimestamp;
    private Date closeTimestamp;
    private double openPrice;
    private double closePrice;
    private double highPrice;
    private double lowPrice;

    @JsonIgnore
    private String isin;

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Date getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(Date openTimestamp) {
        this.openTimestamp = openTimestamp;
    }

    public Date getCloseTimestamp() {
        return closeTimestamp;
    }

    public void setCloseTimestamp(Date closeTimestamp) {
        this.closeTimestamp = closeTimestamp;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }
}
