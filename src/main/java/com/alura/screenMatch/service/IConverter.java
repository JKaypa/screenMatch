package com.alura.screenMatch.service;

public interface IConverter {
    <T> T toObject(String json, Class<T> object);
}
