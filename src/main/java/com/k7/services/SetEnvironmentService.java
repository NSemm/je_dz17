package com.k7.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k7.Menu;
import com.k7.configuration.AppProperties;
import com.k7.httpfactory.HttpRequestFactory;
import com.k7.httpfactory.JsonHttpRequestFactory;
import com.k7.menuaction.*;
import com.k7.services.*;
import com.k7.utility.OutputContacts;
import lombok.AllArgsConstructor;

import java.net.http.HttpClient;
import java.util.*;

@AllArgsConstructor
public class SetEnvironmentService {
    private AppProperties properties;

    public void start() {
        Scanner sc = new Scanner(System.in);
        HttpClient httpClient = HttpClient.newBuilder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        OutputContacts outputContacts = new OutputContacts();
        String baseUri = properties.getBaseUri();
        String filePath = properties.getFilePath();
        String workmode = properties.getWorkmode();
        boolean envIsValid = false;
        if (workmode.equals("api")) {
            HttpRequestFactory httpRequestFactory = new JsonHttpRequestFactory(objectMapper);
            UserService userService = new ApiUserService(httpClient, objectMapper, baseUri,httpRequestFactory);
            ContactService contactService = new ApiContactService(baseUri, objectMapper, userService, httpClient, httpRequestFactory);
            bodyExecute(sc, outputContacts, userService, contactService);
            envIsValid = true;
        }
        if (workmode.equals("file")) {
            UserService userService = new MocksUserService();
            ContactService contactService = new NioFileContactService(filePath);
            bodyExecute(sc, outputContacts, userService, contactService);
            envIsValid = true;
        }
        if (workmode.equals("memory")) {
            UserService userService = new MocksUserService();
            ContactService contactService = new InMemoryContactService();
            bodyExecute(sc, outputContacts, userService, contactService);
            envIsValid = true;
        }
        if (!envIsValid)
            throw new UnsupportedOperationException("Invalid work mode");
    }

    private void bodyExecute(Scanner sc, OutputContacts outputContacts, UserService userService, ContactService contactService) {
        Menu menu = new Menu(sc);
        menu.addAction(new RegistrationUserMenuAction(userService));
        menu.addAction(new LoginUserMenuAction(userService));
        menu.addAction(new ReadAllContactsMenuAction(contactService, outputContacts, userService));
        menu.addAction(new AddContactMenuAction(contactService, userService));
        menu.addAction(new SearchByValueContactsMenuAction(sc, contactService, outputContacts, userService));
        menu.addAction(new SearchByNameContactsMenuAction(sc, contactService, outputContacts, userService));
        menu.addAction(new ExitMenuAction());
        menu.run();
    }

//    public String setContactService(String workmode) {
//        Map<String, String> varEnv = new HashMap<>();
//        varEnv.put("api", "ApiContactService(baseUri, objectMapper, userService, httpClient)");
//        varEnv.put("file", "NioFileContactService(properties.getProperty(\"file.path\")");
//        varEnv.put("memory", "InMemoryContactService()");
//        List<String> list = varEnv.entrySet().stream()
//                .filter(c -> workmode.equals(c.getKey()))
//                .map(c -> c.getValue())
//                .collect(Collectors.toList());
//        return list.get(0);
//    }

}
