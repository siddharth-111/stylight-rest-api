package com.example.SpringBoot.websocket.handler;

import com.example.SpringBoot.Model.Instrument;
import com.example.SpringBoot.Model.api.InstrumentsDataResponse;
import com.example.SpringBoot.Model.api.InstrumentsWsResponse;
import com.example.SpringBoot.Model.enums.InstrumentsEventType;
import com.example.SpringBoot.exception.BadRequestException;
import com.example.SpringBoot.service.serviceImpl.InstrumentsServiceImpl;
import com.example.SpringBoot.service.serviceInterface.InstrumentsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.ExceptionWebSocketHandlerDecorator;

/**
 * Handle server connection.
 */
@Configuration
public class InstrumentsWsHandler implements WebSocketHandler {

    @Autowired
    InstrumentsServiceImpl instrumentsService;

    Logger logger = LoggerFactory.getLogger(InstrumentsWsHandler.class);

    /**
     * Called when WS connects to the server.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("Web socket Instruments handler started");
    }

    /**
     * Main method to handle server messages.
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        try
        {
            // Get payload
            String payload = (String) message.getPayload();

            // Convert payload to instruments web socket response
            ObjectMapper mapper = new ObjectMapper();
            InstrumentsWsResponse instrumentsWsResponse  = mapper.readValue(payload, InstrumentsWsResponse.class);

            // Create instrument model out of instruments ws response
            Instrument instrument = new Instrument(instrumentsWsResponse.getData().getIsin(), instrumentsWsResponse.getData().getDescription());

            // Add or delete instrument based on type of event
            if(instrumentsWsResponse.getType() == InstrumentsEventType.ADD)
            {
                instrumentsService.saveInstrument(instrument);
            }
            else if(instrumentsWsResponse.getType() == InstrumentsEventType.DELETE)
            {
                instrumentsService.deleteInstrument(instrument);
            }
            else
            {
                throw new Exception("Invalid instrument event type :" + instrumentsWsResponse.getType());
            }
        }
        catch (Exception e)
        {
            logger.error("Error in saving instruments, the error is " + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * Error handling.
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("Error in transport " + exception.getLocalizedMessage(), exception);
    }

    /**
     * Called when WS is closed.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("Web socket Instruments handler stopped");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}