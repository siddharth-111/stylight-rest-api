package com.example.SpringBoot.config;

import java.time.Instant;
import static java.time.Instant.now;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.SpringBoot.Model.Candlestick;
import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.Model.Quote;
import com.example.SpringBoot.service.serviceInterface.CandlesticksService;
import com.example.SpringBoot.service.serviceInterface.InstrumentsService;
import com.example.SpringBoot.service.serviceInterface.QuotesService;


@Configuration
@EnableScheduling
public class AggregatorScheduler {

    Logger logger = LoggerFactory.getLogger(AggregatorScheduler.class);

    @Autowired
    QuotesService quotesService;

    @Autowired
    InstrumentsService instrumentsService;

    @Autowired
    CandlesticksService candlesticksService;

    @Scheduled(cron = "0 * * * * *")
    public void createCandleSticks()
    {
        logger.info("Creating candle sticks data every one minute");
        try
        {
            List<Instrument> instruments = instrumentsService.getInstruments();

            for (Instrument instrument: instruments) {

                // Create open time and close time to fetch quotes
                Instant now = now().truncatedTo(ChronoUnit.MINUTES);
                Date closeTime = Date.from(now);
                Date openTime = Date.from(now.minus(1, ChronoUnit.MINUTES));

                String isin = instrument.getIsin();

                // Find all related quotes of isin between open time and close time
                List<Quote> quotes = quotesService.findRelatedQuotesBetweenTime(isin, openTime, closeTime);

                if(quotes.size() > 0)
                {
                    logger.debug("Generating the candlestick for isin:" + isin + " between openTime:" + openTime + " and closeTime: " + closeTime);

                    Candlestick candlestick = createCandleStickFromQuotes(quotes, isin, openTime, closeTime);
                    candlesticksService.saveCandlestick(candlestick);
                } else
                {
                    logger.debug("No quotes available to generate candlestick for isin:" + isin + " between openTime:" + openTime + " and closeTime: " + closeTime);
                }
            }
        }
        catch (Exception e)
        {
            logger.error("Error in creating candlesticks, the error is :" + e.getLocalizedMessage(), e);
        }
    }


    @Scheduled(cron = "0 0/2 * * * *")
    public void quotesCleanup()
    {
        logger.info("Performing the quotes clean up every two minutes");

        try
        {
            // Create a timestamp of current minus two minutes
            Instant instant = now().truncatedTo(ChronoUnit.MINUTES).minus(2, ChronoUnit.MINUTES);
            Date currentDateTime = Date.from(instant);

            logger.debug("Deleting the quotes before the time: " + currentDateTime);

            // Delete all the quotes from database older than two minutes
            quotesService.deleteQuotesBeforeTime(currentDateTime);

        }
        catch (Exception e)
        {
            logger.error("Error in deleting quotes from database, the error is :" + e.getLocalizedMessage(), e);
        }

    }

    @Scheduled(cron = "0 0 * * * *")
    public void candleSticksCleanup()
    {
        logger.info("Performing the candlestick clean up every hour");

        try
        {
            // Create a timestamp of current minus an hour
            Instant instant = now().truncatedTo(ChronoUnit.MINUTES).minus(1, ChronoUnit.HOURS);
            Date previousHour = Date.from(instant);

            logger.debug("Deleting the candlestick before the time: " + previousHour);

            // Delete all the quotes from database older than two minutes
            candlesticksService.deleteCandlesticksBeforeTime(previousHour);

        }
        catch (Exception e)
        {
            logger.error("Error in deleting quotes from database, the error is :" + e.getLocalizedMessage(), e);
        }

    }

    private Candlestick createCandleStickFromQuotes(List<Quote> quotes, String isin, Date openTime, Date closeTime) throws Exception
    {
        Candlestick candlestick = new Candlestick();

        candlestick.setIsin(isin);

        // Set open and close time stamp
        candlestick.setOpenTimestamp(openTime);
        candlestick.setCloseTimestamp(closeTime);

        // Set open and close price
        candlestick.setOpenPrice(quotes.get(quotes.size() - 1).getPrice());
        candlestick.setClosePrice(quotes.get(0).getPrice());

        // Set low and high price
        candlestick.setLowPrice(quotes.stream()
                    .min(Comparator.comparing(Quote :: getPrice))
                    .orElseThrow(NoSuchElementException::new)
                    .getPrice());

        candlestick.setHighPrice(quotes.stream()
                    .max(Comparator.comparing(Quote :: getPrice))
                    .orElseThrow(NoSuchElementException::new)
                    .getPrice());

        return candlestick;
    }
}
