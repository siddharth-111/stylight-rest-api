package com.example.SpringBoot.websocket.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Component
public class WebSocketConfigHelper {

    public WebSocketConnectionManager getWebSocketConnectionManager(String webSocketUri, WebSocketHandler webSocketHandler)
    {
        //Generates a web socket connection
        WebSocketConnectionManager manager = new WebSocketConnectionManager(
                new StandardWebSocketClient(),
                webSocketHandler, //Must be defined to handle messages
                webSocketUri);

        //Will connect as soon as possible
        manager.setAutoStartup(true);

        return manager;
    }
}
