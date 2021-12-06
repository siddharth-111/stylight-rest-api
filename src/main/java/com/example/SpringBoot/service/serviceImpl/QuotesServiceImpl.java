package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.Model.Quote;
import com.example.SpringBoot.dao.InstrumentDAO;
import com.example.SpringBoot.dao.QuoteDAO;
import com.example.SpringBoot.exception.BadRequestException;
import com.example.SpringBoot.repository.InstrumentsRepository;
import com.example.SpringBoot.repository.QuotesRepository;
import com.example.SpringBoot.service.serviceInterface.QuotesService;
import com.example.SpringBoot.service.utils.WebsocketsServiceHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotesServiceImpl implements QuotesService {

    @Autowired
    QuotesRepository quotesRepository;

    @Autowired
    ModelMapper modelMapper;

    public void saveQuote(Quote quote) throws Exception
    {
        try
        {
            QuoteDAO quoteDAO = modelMapper.map(quote, QuoteDAO.class);
            quotesRepository.save(quoteDAO);
        }
        catch (Exception e)
        {
            throw new BadRequestException("Error in saving the quote, error is :" + e);
        }
    }

    public void deleteQuotesBeforeTime(Date closeTime) throws Exception
    {
        quotesRepository.deleteByCreationDateLessThan(closeTime);
    }

    public List<Quote> findRelatedQuotesBetweenTime(String isin, Date openTime, Date closeTime) throws Exception
    {
        List<QuoteDAO> quoteDAOS = quotesRepository.findRelatedQuotesBetweenTimeNative(isin, openTime, closeTime);

        List<Quote> quotes = quoteDAOS.stream().map(quoteDAO -> modelMapper.map(quoteDAO, Quote.class)).collect(Collectors.toList());
        return quotes;

    }
}
