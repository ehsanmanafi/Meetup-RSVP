package com.server.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MyScheduledTask {
    private final ObjectMapper objectMapper = new ObjectMapper(); // To convert Object to JSON
    List<String> cityItems = List.of("London", "Hamburg", "Paris");
    List<String> randomItemsN1 = List.of("20", "30", "40");
    List<String> randomItemsN2 = List.of("0.5", "-0.5", "0.8");
    // Method that runs periodically and sends messages
    @Scheduled(fixedRate = 1000) // Runs every 1 second
    public void executeTask() throws IOException {
        WebSocketHandler webSocketHandler = new WebSocketHandler();
        // Send messages to all clients
        for (WebSocketSession s : webSocketHandler.getSessions()) {
            if (s.isOpen()) {
                //Unreal: Just For Test
                String randomCity = cityItems.get(ThreadLocalRandom.current().nextInt(cityItems.size()));
                String randomN1 = randomItemsN1.get(ThreadLocalRandom.current().nextInt(randomItemsN1.size()));
                String randomN2 = randomItemsN2.get(ThreadLocalRandom.current().nextInt(randomItemsN2.size()));

                Message response = new Message("Server", "{\"visibility\": \"public\", \"response\": [\"response\"], \"guests\": [\"guests\"], \"mtime\": 1489925473189, \"group_name\": \"Vive la diff\\u00e9rence: French classes with\\u2b50a\\u2b50twist\", \"group_city\": \"" + randomCity + "\", \"group_lat\": " + randomN1 + ", \"group_lon\": " + randomN2 + ", \"group_country\": \"gb\"}");
                String jsonResponse = objectMapper.writeValueAsString(response);
                s.sendMessage(new TextMessage(jsonResponse));

//                s.sendMessage(new TextMessage("<<<Line an Advertisement>>> Regular Message From Server Over Socket"));
            }
        }
    }
}
