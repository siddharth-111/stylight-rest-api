package com.example.SpringBoot.Model;

import java.util.Date;

public class Quote {

    private long id;
    private String isin;

    private double price;
    private Date creationDate;

    public Quote() {

    }

    public Quote(String isin, double price)
    {
        this.isin = isin;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsin() {
        return isin;
    }


    public void setIsin(String isin) {
        this.isin = isin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
