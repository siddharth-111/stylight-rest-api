package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.Candlestick;
import com.example.SpringBoot.dao.CandlestickDAO;
import com.example.SpringBoot.repository.CandlesticksRepository;
import com.example.SpringBoot.service.serviceInterface.CandlesticksService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
