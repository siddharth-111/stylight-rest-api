package com.example.SpringBoot.service.serviceInterface;

import com.example.SpringBoot.Model.Instrument;

public interface InstrumentsService {
    Instrument saveInstrument(Instrument instrument) throws Exception;
    void deleteInstrument(Instrument instrument) throws Exception;
}
