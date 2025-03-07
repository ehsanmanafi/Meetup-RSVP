package com.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;

public class WebSocketClient {



    public void connect() {
        try {
            StandardWebSocketClient client = new StandardWebSocketClient();
            WebSocketSession session = client.doHandshake(new MyWebSocketHandler(), String.valueOf(new URI("ws://localhost:8585/ws"))).get();

            // Send message to server

            Message message = new Message("Client", "Hello, Server!");
            String jsonMessage = new ObjectMapper().writeValueAsString(message);
            session.sendMessage(new TextMessage(jsonMessage));

            Thread.sleep(5000); // Waiting to receive a message from the server
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

