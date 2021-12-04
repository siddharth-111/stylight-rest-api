package com.example.SpringBoot.service.serviceInterface;

import com.example.SpringBoot.Model.Instrument;

public interface InstrumentsService {
    Instrument saveInstruments(Instrument instrument) throws Exception;
}
