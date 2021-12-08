package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.Quote;
import com.example.SpringBoot.dao.QuoteDAO;
import com.example.SpringBoot.exception.BadRequestException;
import com.example.SpringBoot.repository.QuotesRepository;
import com.example.SpringBoot.service.serviceInterface.QuotesService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(InstrumentsServiceImpl.class);

    public void saveQuote(Quote quote) throws Exception
    {
        try
        {
            logger.debug("Saving quote for the isin: " + quote.getIsin());
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
        logger.debug("deleting quotes before time: " + closeTime);

        quotesRepository.deleteByCreationDateLessThan(closeTime);
    }

    public List<Quote> findRelatedQuotesBetweenTime(String isin, Date openTime, Date closeTime) throws Exception
    {
        logger.debug("finding related quotes for isin: " + isin);

        List<QuoteDAO> quoteDAOS = quotesRepository.findRelatedQuotesBetweenTimeNative(isin, openTime, closeTime);

        List<Quote> quotes = quoteDAOS.stream().map(quoteDAO -> modelMapper.map(quoteDAO, Quote.class)).collect(Collectors.toList());

        return quotes;
    }

    public void deleteQuotesByIsinBeforeCreationTime(String isin, Date creationDate) throws Exception
    {
        logger.debug("deleting quotes for isin: " + isin + " before time: " + creationDate);

        quotesRepository.deleteByIsinCreationDateLessThanNative(isin, creationDate);
    }
}
