package com.k7.requestfactory;

import com.k7.services.UserService;

import java.net.http.HttpRequest;

public interface HttpRequestFactory {
    HttpRequest createGetRequest(String url);
    HttpRequest createGetRequestAuth(String url, UserService userService);
    HttpRequest createPostRequest(Object req, String url);
    HttpRequest createPostRequestAuth(Object req, String url, UserService userService);
}
