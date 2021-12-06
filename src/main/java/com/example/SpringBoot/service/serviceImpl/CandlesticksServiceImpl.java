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

        return candlesticks.size() != 0 ? fillRemainingCandlesticks(candlesticks) : candlesticks;
    }

    public List<Candlestick> fillRemainingCandlesticks(List<Candlestick> candlesticks)
    {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        while(candlesticks.size() < 30)
        {
            Candlestick candlestick = candlesticks.get(0);
            Date newOpenTime = candlestick.getCloseTimestamp();
            Date newCloseTime = Date.from(newOpenTime.toInstant().truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES));
            candlestick.setOpenTimestamp(newOpenTime);
            candlestick.setCloseTimestamp(newCloseTime);
            candlesticks.add(0, candlestick);
        }

        return candlesticks;
    }
}
