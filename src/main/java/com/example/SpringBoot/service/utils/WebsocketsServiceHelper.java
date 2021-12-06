package com.example.SpringBoot.service.utils;

import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.Model.Quote;
import com.example.SpringBoot.dao.InstrumentDAO;
import com.example.SpringBoot.dao.QuoteDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebsocketsServiceHelper {

    @Autowired
    ModelMapper modelMapper;

    public InstrumentDAO convertToInstrumentDAO(Instrument instrument)
    {
        return modelMapper.map(instrument, InstrumentDAO.class);
    }

    public Instrument convertToInstrument(InstrumentDAO instrumentDAO)
    {
        return modelMapper.map(instrumentDAO, Instrument.class);
    }

    public QuoteDAO convertToQuoteDAO(Quote quote)
    {
        return modelMapper.map(quote, QuoteDAO.class);
    }

}
