package ru.iaygi.kafka.data;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class JsonMessage {

    public static String createJsonMessage() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> message = new HashMap<>();
        message.put("id", 1);
        message.put("name", "TestFile");
        message.put("age", 24);
        return objectMapper.writeValueAsString(message);
    }
}
