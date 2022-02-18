package com.ratepay.challenge.model;

import java.util.Date;

public class Instrument {
    private String isin;
    private String description;
    private Date creationDate;

    public Instrument() {

    }

    public Instrument(String isin, String description)
    {
        this.isin = isin;
        this.description = description;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
