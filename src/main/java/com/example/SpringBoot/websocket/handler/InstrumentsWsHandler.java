package com.example.SpringBoot.websocket.handler;

import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.Model.api.InstrumentsDataResponse;
import com.example.SpringBoot.Model.api.InstrumentsWsResponse;
import com.example.SpringBoot.service.serviceInterface.InstrumentsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.ExceptionWebSocketHandlerDecorator;

/**
 * Handle server connection.
 */
public class InstrumentsWsHandler implements WebSocketHandler {

    @Autowired
    InstrumentsService instrumentsService;
    /**
     * Called when WS connects to the server.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    /**
     * Main method to handle server messages.
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = (String) message.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        InstrumentsWsResponse instrumentsWsResponse  = mapper.readValue(payload, InstrumentsWsResponse.class);
        Instrument instrument = new Instrument(instrumentsWsResponse.getData().getIsin(), instrumentsWsResponse.getData().getDescription());
//        instrumentsService.saveInstruments(instrument);
    }

    /**
     * Error handling.
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    /**
     * Called when WS is closed.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        // TODO Auto-generated method stub
        return false;
    }

}