package com.client;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {
    private  KafkaProducer kafkaProducer=new KafkaProducer();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Received from server: " + message.getPayload());
        // Sample Data
        kafkaProducer.sendMessage("app-rsvp", message.getPayload());
    }
}
