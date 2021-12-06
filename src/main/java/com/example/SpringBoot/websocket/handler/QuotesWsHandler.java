package com.example.SpringBoot.websocket.handler;

import com.example.SpringBoot.Model.Quote;
import com.example.SpringBoot.Model.api.QuotesWsResponse;
import com.example.SpringBoot.Model.enums.QuotesEventType;
import com.example.SpringBoot.exception.BadRequestException;
import com.example.SpringBoot.service.serviceImpl.QuotesServiceImpl;
import com.example.SpringBoot.service.serviceInterface.QuotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Handle server connection.
 */
@Component
public class QuotesWsHandler implements WebSocketHandler {

    Logger logger = LoggerFactory.getLogger(QuotesWsHandler.class);

    @Autowired
    QuotesService quotesService;

    /**
     * Called when WS connects to the server.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("Web socket quotes handler started");
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
            QuotesWsResponse quotesWsResponse  = mapper.readValue(payload, QuotesWsResponse.class);

            // Create quotes model out of quotes web socket response
            Quote quote = new Quote(quotesWsResponse.getData().getIsin(), quotesWsResponse.getData().getPrice());

            if(quotesWsResponse.getType() == QuotesEventType.QUOTE)
            {
                quotesService.saveQuote(quote);
            } else
            {
                throw new Exception("Invalid quote event type :" + quotesWsResponse.getType());
            }

        }
        catch (Exception e)
        {
            logger.error("Error in saving quotes, the error is " + e.getMessage(), e);
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
        logger.debug("Web socket quotes handler stopped");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
