package com.alura.screenMatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIQuery {
    public String getData(String url){
        HttpClient client = HttpClient.newHttpClient();
        URI endpoint = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder().uri(endpoint).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
