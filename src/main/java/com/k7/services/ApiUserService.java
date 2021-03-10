package com.k7.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k7.contacts.User;
import com.k7.dto.*;
import com.k7.requestfactory.HttpRequestFactory;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ApiUserService implements UserService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUri;
    private final HttpRequestFactory httpRequestFactory;
    private String token = null;
    private LocalDateTime tokenTime = null;


    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void registration(User user) {
        RegRequest req = new RegRequest();
        req.setLogin(user.getLogin());
        req.setPassword(user.getPassword());
        req.setDateBorn(user.getDateBorn().toString());
        try {
            HttpRequest request = httpRequestFactory.createPostRequest(req, baseUri + "/register");
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            StatusResponse statusResponse = objectMapper.readValue(response.body(), StatusResponse.class);
            if ("error".equals(statusResponse.getStatus())) {
                throw new RuntimeException("Operation error: " + statusResponse.getError());
            }
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void auth(User user) {

        LoginResponse.LoginRequest req = new LoginResponse.LoginRequest();
        req.setLogin(user.getLogin());
        req.setPassword(user.getPassword());
        try {
            HttpRequest request = httpRequestFactory.createPostRequest(req, baseUri + "/login");
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            LoginResponse loginResponse = objectMapper.readValue(response.body(), LoginResponse.class);
            if ("ok".equals(loginResponse.getStatus())) {
                token = loginResponse.getToken();
                tokenTime = LocalDateTime.now();
            }
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean isAuth() {
        if (token == null || tokenTime == null) return false;
        return Duration.between(LocalDateTime.now(), tokenTime).toHours() < 59;
    }

    @Override
    public List<User> getAll() {
        HttpRequest request = httpRequestFactory.createGetRequest(baseUri + "/users");
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            UserResponse userResponse = objectMapper.readValue(response.body(), UserResponse.class);
            return userResponse.getUsers().stream().map(c -> {
                User user = new User();
                user.setLogin(c.getLogin());
                user.setDateBorn(c.getDateBorn());
                return user;
            })
                    .collect(Collectors.toList());

        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
        return Collections.emptyList();
    }
}
