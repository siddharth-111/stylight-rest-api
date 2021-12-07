package com.example.SpringBoot.service.serviceImpl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.SpringBoot.config.AggregatorScheduler;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBoot.Model.Candlestick;
import com.example.SpringBoot.dao.CandlestickDAO;
import com.example.SpringBoot.repository.CandlesticksRepository;
import com.example.SpringBoot.service.serviceInterface.CandlesticksService;


import static java.time.Instant.now;

@Service
public class CandlesticksServiceImpl implements CandlesticksService {

    Logger logger = LoggerFactory.getLogger(CandlesticksServiceImpl.class);

    @Autowired
    CandlesticksRepository candlesticksRepository;

    @Autowired
    ModelMapper modelMapper;

    public void saveCandlestick(Candlestick candlestick) throws Exception
    {
        logger.debug("save candlestick for isin: " + candlestick.getIsin());

        CandlestickDAO candlestickDAO = modelMapper.map(candlestick, CandlestickDAO.class);

        candlesticksRepository.save(candlestickDAO);
    }

    public void deleteCandlesticksBeforeTime(Date closeTime) throws Exception
    {
        logger.debug("deleting candlesticks before time: " + closeTime);
        candlesticksRepository.deleteByCreationDateLessThan(closeTime);
    }

    public List<Candlestick> findCandlesticks(String isin) throws Exception
    {
        logger.debug("finding candlesticks for isin: " + isin);

        // Create open time and close time to fetch quotes
        Instant now = now().truncatedTo(ChronoUnit.MINUTES);
        Date closeTime = Date.from(now);
        Date openTime = Date.from(now.minus(30, ChronoUnit.MINUTES));

        // find candlesticks with isin within open and close time
        List<CandlestickDAO> candlestickDAOS = candlesticksRepository.findRelatedCandlesticksBetweenTimeNative(isin, openTime, closeTime);

        List<Candlestick> candlesticks = candlestickDAOS.stream()
                        .map(candlestickDAO -> modelMapper.map(candlestickDAO, Candlestick.class))
                        .collect(Collectors.toList());

        return candlesticks.size() != 0 ? fillEmptyCandlesticks(candlesticks, openTime, closeTime) : candlesticks;
    }

    public List<Candlestick> fillEmptyCandlesticks(List<Candlestick> candlesticks, Date openTime, Date closeTime) throws Exception
    {

        candlesticks = fillIntermediateCandlesticks(candlesticks);

        fillLeadingCandlesticks(candlesticks, closeTime);

        fillTrailingCandlesticks(candlesticks, openTime);

        return candlesticks;
    }

    private List<Candlestick> fillIntermediateCandlesticks(List<Candlestick> candlesticks) throws CloneNotSupportedException {

        logger.info("fill intermediate candlesticks");

        // Get open time and close time to fill intermediate candles
        Date openTime = candlesticks.get(0).getOpenTimestamp();
        Date closeTime = candlesticks.get(candlesticks.size() - 1).getCloseTimestamp();

        // insert first candle in intermediate candles to enable picking previous candles in loop
        List<Candlestick> intermediateCandlesticks = new ArrayList<>();
        intermediateCandlesticks.add(candlesticks.get(0));
        openTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));

        while(openTime.getTime() < closeTime.getTime())
        {
            Date finalOpenTime = openTime;

            // If a candlestick exists for the timestamp, make it the previous candle or use intermediate candlesticks last result as previous candlestick
            Candlestick existingCandlestick = candlesticks.stream()
                            .filter(candlestick -> candlestick.getOpenTimestamp().getTime() == finalOpenTime.getTime())
                            .findFirst().orElse(null);


            Candlestick candlestick = (existingCandlestick != null) ? (Candlestick) existingCandlestick.clone()
                    : (Candlestick) intermediateCandlesticks.get(intermediateCandlesticks.size() - 1).clone();

            // Set open and close timestamp for the candlestick
            candlestick.setOpenTimestamp(openTime);
            openTime = Date.from(openTime.toInstant().plus(1, ChronoUnit.MINUTES));
            candlestick.setCloseTimestamp(openTime);

            // Push to results
            intermediateCandlesticks.add(candlestick);
        }

        return intermediateCandlesticks;
    }

    private void fillLeadingCandlesticks(List<Candlestick> candlesticks, Date closeTime) throws Exception
    {
        logger.debug("fill leading candlesticks for close time: " + closeTime);

        Date leadingCloseTime = candlesticks.get(candlesticks.size() - 1).getCloseTimestamp();

        while(leadingCloseTime.getTime() < closeTime.getTime())
        {
            // Get the previous candle to fill leading candlestick
            Candlestick candlestick = (Candlestick) candlesticks.get(candlesticks.size() - 1).clone();

            // Set open and close timestamp for the candlestick
            candlestick.setOpenTimestamp(leadingCloseTime);
            leadingCloseTime = Date.from(leadingCloseTime.toInstant()
                    .plus(1, ChronoUnit.MINUTES));
            candlestick.setCloseTimestamp(leadingCloseTime);

            // Push to results
            candlesticks.add(candlestick);
        }
    }

    private void fillTrailingCandlesticks(List<Candlestick> candlesticks, Date openTime) throws Exception
    {
        logger.debug("fill trailing candlesticks for open time: " + openTime);

        Date trailingOpenTime = candlesticks.get(0).getOpenTimestamp();

        while(trailingOpenTime.getTime() > openTime.getTime())
        {
            // Get the previous candle to fill trailing candlestick
            Candlestick candlestick = (Candlestick) candlesticks.get(0).clone();

            // Set open and close timestamp for the candlestick
            candlestick.setCloseTimestamp(trailingOpenTime);
            trailingOpenTime = Date.from(trailingOpenTime.toInstant()
                    .minus(1, ChronoUnit.MINUTES));
            candlestick.setOpenTimestamp(trailingOpenTime);

            // Add the trailing candlestick to the beginning of the list
            candlesticks.add(0, candlestick);
        }
    }
}
