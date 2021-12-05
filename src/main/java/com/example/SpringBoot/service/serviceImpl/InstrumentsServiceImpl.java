package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.dao.InstrumentDAO;
import com.example.SpringBoot.exception.BadRequestException;
import com.example.SpringBoot.exception.ResourceNotFoundException;
import com.example.SpringBoot.repository.InstrumentsRepository;
import com.example.SpringBoot.service.utils.WebsocketsServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
public class InstrumentsServiceImpl {

    @Autowired
    InstrumentsRepository instrumentsRepository;

    @Autowired
    private WebsocketsServiceHelper serviceHelper;

    public Instrument saveInstrument(Instrument instrument)
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

    public void deleteInstrument(Instrument instrument) throws Exception
    {
        final String isin = instrument.getIsin();
        instrumentsRepository.findById(isin)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete as there exists the following isin: " + isin));

        instrumentsRepository.deleteById(isin);
    }

}
