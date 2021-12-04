package com.example.SpringBoot.config;

import com.example.SpringBoot.config.utils.ConfigHelper;
import com.example.SpringBoot.service.utils.ServiceHelper;
import com.example.SpringBoot.websocket.handler.InstrumentsWsHandler;
import com.example.SpringBoot.websocket.handler.QuotesWsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Configuration
public class WebSocketConfig {

    private final String instrumentsWsUri = "ws://localhost:8032/instruments";
    private final String quotesWsUri = "ws://localhost:8032/quotes";

    @Autowired
    ConfigHelper configHelper;

    @Bean
    public WebSocketConnectionManager instrumentsWsConnectionManager() {
        return configHelper.getWebSocketConnectionManager(this.instrumentsWsUri, new InstrumentsWsHandler());
    }

    @Bean
    public WebSocketConnectionManager quotesWsConnectionManager() {
        return configHelper.getWebSocketConnectionManager(this.quotesWsUri, new QuotesWsHandler());
    }

}
