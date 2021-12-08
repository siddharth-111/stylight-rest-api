package com.example.SpringBoot.service;

import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.dao.InstrumentDAO;
import com.example.SpringBoot.exception.ResourceNotFoundException;
import com.example.SpringBoot.repository.InstrumentsRepository;
import com.example.SpringBoot.service.serviceInterface.InstrumentsService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@SpringBootTest
public class InstrumentServiceTest {

    @Autowired
    private InstrumentsService instrumentsService;

    @Autowired
    private InstrumentsRepository instrumentsRepository;

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void shouldSaveInstrument() throws Exception {
        Instrument instrument = new Instrument();
        instrument.setIsin("TEST_AI1715314885");
        instrument.setDescription("Test instrument");

        instrumentsService.saveInstrument(instrument);

        Optional<InstrumentDAO> instrumentResponse = instrumentsRepository.findById("TEST_AI1715314885");

        assertTrue(instrumentResponse.isPresent());

        instrumentResponse.ifPresent(instrumentDAO -> {
            assertEquals(instrumentDAO.getIsin(), "TEST_AI1715314885");
            assertEquals(instrumentDAO.getDescription(), "Test instrument");
        });

        instrumentsService.deleteInstrument(instrument);
    }

    @Test
    public void shouldDeleteInstrument() throws Exception {
        Instrument instrument = new Instrument();
        instrument.setIsin("TEST_AI1715314885");
        instrument.setDescription("Test instrument");

        instrumentsService.saveInstrument(instrument);

        Optional<InstrumentDAO> instrumentResponse = instrumentsRepository.findById("TEST_AI1715314885");

        assertTrue(instrumentResponse.isPresent());

        instrumentsService.deleteInstrument(instrument);

        instrumentResponse = instrumentsRepository.findById("TEST_AI1715314885");

        assertTrue(instrumentResponse.isEmpty());
    }

    @Test
    public void shouldFailToDeleteInstrument() {
        Instrument instrument = new Instrument();
        instrument.setIsin("TEST_AI1715314885");
        instrument.setDescription("Test instrument");

        assertThrows(ResourceNotFoundException.class, () -> instrumentsService.deleteInstrument(instrument));

    }
}
