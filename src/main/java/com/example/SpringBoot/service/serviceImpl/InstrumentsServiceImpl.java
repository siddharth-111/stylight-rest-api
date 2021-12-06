package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.dao.InstrumentDAO;
import com.example.SpringBoot.exception.BadRequestException;
import com.example.SpringBoot.exception.ResourceNotFoundException;
import com.example.SpringBoot.repository.InstrumentsRepository;
import com.example.SpringBoot.service.serviceInterface.InstrumentsService;
import com.example.SpringBoot.service.utils.WebsocketsServiceHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstrumentsServiceImpl implements InstrumentsService {

    @Autowired
    InstrumentsRepository instrumentsRepository;

    @Autowired
    ModelMapper modelMapper;

    public void saveInstrument(Instrument instrument)
    {
        try
        {
            InstrumentDAO instrumentDAO = modelMapper.map(instrument, InstrumentDAO.class);
            instrumentsRepository.save(instrumentDAO);
        }
        catch (Exception e)
        {
            throw new BadRequestException("Error in saving the instrument, error is :" + e);
        }
    }

    public void deleteInstrument(Instrument instrument) throws Exception
    {
        final String isin = instrument.getIsin();

        instrumentsRepository.findById(isin)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete as there exists no following isin: " + isin));

        instrumentsRepository.deleteById(isin);
    }

    public List<Instrument> getInstruments() throws Exception
    {
        List<InstrumentDAO> instrumentDAOS = instrumentsRepository.findAll();

        List<Instrument> instruments = instrumentDAOS.stream().
                map(instrumentDAO -> modelMapper.map(instrumentDAO, Instrument.class)).collect(Collectors.toList());

        return instruments;
    }

}
