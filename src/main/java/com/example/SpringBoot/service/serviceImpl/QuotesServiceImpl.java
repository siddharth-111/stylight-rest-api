package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.Model.Quote;
import com.example.SpringBoot.dao.InstrumentDAO;
import com.example.SpringBoot.dao.QuoteDAO;
import com.example.SpringBoot.exception.BadRequestException;
import com.example.SpringBoot.repository.InstrumentsRepository;
import com.example.SpringBoot.repository.QuotesRepository;
import com.example.SpringBoot.service.utils.WebsocketsServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuotesServiceImpl {

    @Autowired
    QuotesRepository quotesRepository;

    @Autowired
    private WebsocketsServiceHelper serviceHelper;

    public void saveQuote(Quote quote) throws Exception
    {
        try
        {
            QuoteDAO quoteDAO = serviceHelper.convertToQuoteDAO(quote);
            quotesRepository.save(quoteDAO);
        }
        catch (Exception e)
        {
            throw new BadRequestException("Error in saving the quote, error is :" + e);
        }
    }
}
