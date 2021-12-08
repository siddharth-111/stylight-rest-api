package com.example.SpringBoot.service.serviceInterface;

import com.example.SpringBoot.model.Instrument;

import java.util.List;

public interface InstrumentsService {
    void saveInstrument(Instrument instrument) throws Exception;
    void deleteInstrument(Instrument instrument) throws Exception;
    List<Instrument> getInstruments() throws Exception;
}
