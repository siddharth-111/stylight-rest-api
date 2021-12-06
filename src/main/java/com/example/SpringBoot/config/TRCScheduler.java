package com.example.SpringBoot.config;

import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.Model.Quote;
import com.example.SpringBoot.service.serviceImpl.InstrumentsServiceImpl;
import com.example.SpringBoot.service.serviceImpl.QuotesServiceImpl;
import com.example.SpringBoot.websocket.handler.QuotesWsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.time.Instant;
import static java.time.Instant.now;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class TRCScheduler {

    Logger logger = LoggerFactory.getLogger(TRCScheduler.class);

    @Autowired
    QuotesServiceImpl quotesService;

    @Autowired
    InstrumentsServiceImpl instrumentsService;

    @Scheduled(cron = "0 * * * * *")
    public void demoServiceMethod()
    {


        System.out.println("Method executed at every minute. Current time is :: "+ new Date());
    }

    @Scheduled(fixedDelay = 10000)
    public void demoServiceMethod1()
    {
        List<Instrument> instruments = instrumentsService.getInstruments();
        try
        {
            for (Instrument instrument: instruments) {

                Instant now = now().truncatedTo(ChronoUnit.MINUTES);
                Date closeTime = Date.from(now);
                Date openTime = Date.from(now.minus(1, ChronoUnit.MINUTES));

                List<Quote> quotes = quotesService.findRelatedQuotesBetweenTime(instrument.getIsin(), openTime, closeTime);

                logger.debug("Deleting the quotes before the time: ");
            }
        }
        catch (Exception e)
        {
            logger.error("Error in deleting quotes from database, the error is :" + e.getLocalizedMessage(), e);
        }
    }


    @Scheduled(cron = "0 0/2 * * * *")
    public void quotesCleanup()
    {
        logger.debug("Performing the quotes clean up every two minutes");
        try
        {
            Instant instant = now().truncatedTo(ChronoUnit.MINUTES).minus(2, ChronoUnit.MINUTES);
            Date currentDateTime = Date.from(instant);

            logger.debug("Deleting the quotes before the time: " + currentDateTime);
            quotesService.deleteQuotesBeforeTime(currentDateTime);

        }
        catch (Exception e)
        {
            logger.error("Error in deleting quotes from database, the error is :" + e.getLocalizedMessage(), e);
        }

    }
}
