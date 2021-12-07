package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.Candlestick;
import com.example.SpringBoot.Model.Quote;
import com.example.SpringBoot.dao.CandlestickDAO;
import com.example.SpringBoot.dao.QuoteDAO;
import com.example.SpringBoot.repository.CandlesticksRepository;
import com.example.SpringBoot.service.serviceInterface.CandlesticksService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.Instant.now;

@Service
public class CandlesticksServiceImpl implements CandlesticksService {

    @Autowired
    CandlesticksRepository candlesticksRepository;

    @Autowired
    ModelMapper modelMapper;


    public void saveCandlestick(Candlestick candlestick) throws Exception
    {
        CandlestickDAO candlestickDAO = modelMapper.map(candlestick, CandlestickDAO.class);
        candlesticksRepository.save(candlestickDAO);
    }

    public List<Candlestick> findCandlesticks(String isin) throws Exception
    {
        // Create open time and close time to fetch quotes
        Instant now = now().truncatedTo(ChronoUnit.MINUTES);
        Date closeTime = Date.from(now);
        Date openTime = Date.from(now.minus(30, ChronoUnit.MINUTES));

        List<CandlestickDAO> candlestickDAOS = candlesticksRepository.findRelatedCandlesticksBetweenTimeNative(isin, openTime, closeTime);

        List<Candlestick> candlesticks = candlestickDAOS.stream()
                        .map(candlestickDAO -> modelMapper.map(candlestickDAO, Candlestick.class))
                        .collect(Collectors.toList());

        return candlesticks.size() != 0 ? fillLeadingAndTrailingCandlesticks(candlesticks, openTime, closeTime) : candlesticks;
    }

    public List<Candlestick> fillLeadingAndTrailingCandlesticks(List<Candlestick> candlesticks, Date openTime, Date closeTime) throws Exception
    {
        Date leadingCloseTime = candlesticks.get(0).getCloseTimestamp();
        closeTime = new Timestamp(closeTime.getTime());

        while(leadingCloseTime.getTime() < closeTime.getTime())
        {
              Candlestick candlestick = (Candlestick) candlesticks.get(0).clone();
              candlestick.setOpenTimestamp(leadingCloseTime);
              leadingCloseTime = new Timestamp(Date.from(leadingCloseTime.toInstant()
                      .plus(1, ChronoUnit.MINUTES)).getTime());
              candlestick.setCloseTimestamp(leadingCloseTime);
              candlesticks.add(0, candlestick);
        }

        Date trailingCloseTime = candlesticks.get(candlesticks.size() - 1).getOpenTimestamp();
        openTime = new Timestamp(openTime.getTime());

        while(trailingCloseTime.getTime() > openTime.getTime())
        {
            Candlestick candlestick = (Candlestick) candlesticks.get(candlesticks.size() - 1).clone();
            candlestick.setCloseTimestamp(trailingCloseTime);
            trailingCloseTime = new Timestamp(Date.from(trailingCloseTime.toInstant()
                    .minus(1, ChronoUnit.MINUTES)).getTime());
            candlestick.setOpenTimestamp(trailingCloseTime);
            candlesticks.add(candlestick);
        }

        return candlesticks;
    }
}
