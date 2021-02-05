package com.k7;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k7.menuaction.*;
import com.k7.services.ApiContactService;
import com.k7.services.ApiUserService;
import com.k7.services.ContactService;
import com.k7.services.UserService;
import com.k7.utility.OutputContacts;

import java.net.http.HttpClient;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HttpClient httpClient = HttpClient.newBuilder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        String baseUri = "https://mag-contacts-api.herokuapp.com/";
        UserService userService = new ApiUserService(httpClient, objectMapper, baseUri);
        ContactService contactService = new ApiContactService(baseUri, objectMapper, userService, httpClient);
        OutputContacts outputContacts = new OutputContacts();
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

}
