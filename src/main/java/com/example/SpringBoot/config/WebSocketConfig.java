package com.example.SpringBoot.config;

import com.example.SpringBoot.config.utils.ConfigHelper;
import com.example.SpringBoot.websocket.handler.InstrumentsWsHandler;
import com.example.SpringBoot.websocket.handler.QuotesWsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.socket.client.WebSocketConnectionManager;

@Configuration
public class WebSocketConfig {

    @Autowired
    private Environment environment;

    @Autowired
    ConfigHelper configHelper;

    @Bean
    public WebSocketConnectionManager instrumentsWsConnectionManager() {
        return configHelper.getWebSocketConnectionManager(environment.getProperty("wsconfig.uri-prefix") + environment.getProperty("wsconfig.instruments-uri"), new InstrumentsWsHandler());
    }

    @Bean
    public WebSocketConnectionManager quotesWsConnectionManager() {
        return configHelper.getWebSocketConnectionManager(environment.getProperty("wsconfig.uri-prefix") + environment.getProperty("wsconfig.quotes-uri"), new QuotesWsHandler());
    }

}
