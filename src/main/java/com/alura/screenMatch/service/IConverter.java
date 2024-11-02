package com.alura.screenMatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface IConverter {
    <T> T toObject(String json, Class<T> object);
}
