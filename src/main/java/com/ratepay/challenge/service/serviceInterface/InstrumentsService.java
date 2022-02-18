package com.ratepay.challenge.service.serviceInterface;

import com.ratepay.challenge.model.Instrument;

import java.util.List;

public interface InstrumentsService {
    void saveInstrument(Instrument instrument) throws Exception;
    void deleteInstrument(Instrument instrument) throws Exception;
    List<Instrument> getInstruments() throws Exception;
}
