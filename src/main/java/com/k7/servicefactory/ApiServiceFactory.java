package com.k7.servicefactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k7.configuration.AppProperties;
import com.k7.requestfactory.HttpRequestFactory;
import com.k7.requestfactory.JsonHttpRequestFactory;
import com.k7.services.ApiContactService;
import com.k7.services.ApiUserService;
import com.k7.services.ContactService;
import com.k7.services.UserService;
import lombok.RequiredArgsConstructor;

import java.net.http.HttpClient;

@RequiredArgsConstructor
public class ApiServiceFactory implements ServiceFactory {
    private final AppProperties appProperties;

    HttpClient httpClient = HttpClient.newBuilder().build();
    ObjectMapper objectMapper = new ObjectMapper();
    HttpRequestFactory httpRequestFactory = new JsonHttpRequestFactory(objectMapper);
    UserService userService;


    @Override
    public ContactService createContactServices() {
        ContactService contactService = new ApiContactService(
                appProperties.getBaseUri(),
                objectMapper,
                userService,
                httpClient,
                httpRequestFactory);
        return contactService;
    }

    @Override
    public UserService createUserServices() {
        userService = new ApiUserService(
                httpClient,
                objectMapper,
                appProperties.getBaseUri(),
                httpRequestFactory);

        return userService;
    }
}
