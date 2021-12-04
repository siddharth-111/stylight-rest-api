//package com.example.SpringBoot.websocket.client;
//
//import com.example.SpringBoot.websocket.handler.InstrumentsWsHandler;
//import com.example.SpringBoot.websocket.handler.QuotesWsHandler;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.socket.client.WebSocketConnectionManager;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//
//@SpringBootApplication
//public class SimpleWsApplication {
//
//    private final String instrumentsWsUri = "ws://localhost:8032/instruments";
//    private final String quotesWsUri = "ws://localhost:8032/quotes";
//    public static void main(String[] args) {
//        SpringApplication.run(SimpleWsApplication.class, args);
//    }
//
//    @Bean
//    public WebSocketConnectionManager wsConnectionManager() {
//
//        //Generates a web socket connection
//        WebSocketConnectionManager manager = new WebSocketConnectionManager(
//                new StandardWebSocketClient(),
//                new InstrumentsWsHandler(), //Must be defined to handle messages
//                this.instrumentsWsUri);
//
//        //Will connect as soon as possible
//        manager.setAutoStartup(true);
//
//        return manager;
//    }
//
//    @Bean
//    public WebSocketConnectionManager wsConnectionManager1() {
//
//        //Generates a web socket connection
//        WebSocketConnectionManager manager = new WebSocketConnectionManager(
//                new StandardWebSocketClient(),
//                new QuotesWsHandler(), //Must be defined to handle messages
//                this.quotesWsUri);
//
//        //Will connect as soon as possible
//        manager.setAutoStartup(true);
//
//        return manager;
//    }
//
//
//}