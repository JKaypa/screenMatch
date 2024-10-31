package com.alura.screenMatch.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IConverter {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public <T> T toObject(String json, Class<T> object) {
        try {
            return objectMapper.readValue(json, object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
