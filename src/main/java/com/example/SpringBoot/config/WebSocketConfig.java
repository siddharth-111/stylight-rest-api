package com.example.SpringBoot.config;

import com.example.SpringBoot.websocket.handler.InstrumentsWsHandler;
import com.example.SpringBoot.websocket.handler.QuotesWsHandler;
import com.example.SpringBoot.websocket.utils.WebSocketConfigHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.socket.client.WebSocketConnectionManager;

@Configuration
public class WebSocketConfig {

    @Autowired
    WebSocketConfigHelper configHelper;

    @Autowired
    InstrumentsWsHandler instrumentsWsHandler;

    @Autowired
    QuotesWsHandler quotesWsHandler;

    @Value("${wsconfig.uri-prefix}")
    private String wsUriPrefix;

    @Value("${wsconfig.instruments-uri}")
    private String wsInstrumentUri;

    @Value("${wsconfig.quotes-uri}")
    private String wsQuotesUri;

    @Bean
    public WebSocketConnectionManager instrumentsWsConnectionManager() {
        return configHelper.getWebSocketConnectionManager(wsUriPrefix + wsInstrumentUri, instrumentsWsHandler);
    }

    @Bean
    public WebSocketConnectionManager quotesWsConnectionManager() {
        return configHelper.getWebSocketConnectionManager(wsUriPrefix + wsQuotesUri, quotesWsHandler);
    }

}
