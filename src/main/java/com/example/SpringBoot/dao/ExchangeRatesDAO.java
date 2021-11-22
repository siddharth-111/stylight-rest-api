package com.example.SpringBoot.dao;


import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "ExchangeRates")
public class ExchangeRatesDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "baseCurrency")
    private String baseCurrency;

    @Column(name = "targetCurrency")
    private String targetCurrency;

    public ExchangeRatesDAO() {

    }

    public ExchangeRatesDAO(String date, String baseCurrency, String targetCurrency)  throws Exception
    {
        this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date) throws Exception {
        this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
