package com.k7.requestfactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.k7.services.UserService;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpRequest;

@RequiredArgsConstructor
public class JsonHttpRequestFactory implements HttpRequestFactory {
    private final ObjectMapper objectMapper;

    @Override
    public HttpRequest createGetRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "Application/json")
                .build();
    }

    @Override
    public HttpRequest createGetRequestAuth(String url, UserService userService) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "Application/json")
                .header("Content-Type", "Application/json")
                .header("Authorization", "Bearer " + userService.getToken())
                .build();
    }

    @Override
    public HttpRequest createPostRequest(Object req, String url) {
        try {
            return HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(req)))
                    .header("Accept", "Application/json")
                    .header("Content-Type", "Application/json")
                    .build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpRequest createPostRequestAuth(Object req, String url, UserService userService) {
        try {
            return HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(req)))
                    .header("Authorization", "Bearer " + userService.getToken())
                    .header("Accept", "Application/json")
                    .header("Content-Type", "Application/json")
                    .build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
