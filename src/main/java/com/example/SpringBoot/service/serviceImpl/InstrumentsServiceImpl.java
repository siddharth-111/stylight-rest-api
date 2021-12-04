package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.dao.InstrumentDAO;
import com.example.SpringBoot.exception.BadRequestException;
import com.example.SpringBoot.repository.InstrumentsRepository;
import com.example.SpringBoot.service.utils.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentsServiceImpl {

    @Autowired
    InstrumentsRepository instrumentsRepository;

    @Autowired
    private ServiceHelper serviceHelper;

    public Instrument saveInstruments(Instrument instrument)
    {
        try
        {
            InstrumentDAO instrumentDAO = serviceHelper.convertToInstrumentDAO(instrument);
            InstrumentDAO instrumentDAOResponse = instrumentsRepository.save(instrumentDAO);

            return serviceHelper.convertToInstrument(instrumentDAOResponse);
        }
        catch (Exception e)
        {
            throw new BadRequestException("Error in saving the instrument, error is :" + e);
        }
    }
}
