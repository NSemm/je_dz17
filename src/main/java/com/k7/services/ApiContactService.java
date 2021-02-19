package com.k7.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k7.contacts.Contact;
import com.k7.contacts.ContactType;
import com.k7.dto.ContactsResponse;
import com.k7.dto.StatusResponse;
import com.k7.requestfactory.HttpRequestFactory;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ApiContactService implements ContactService {
    private final String baseUri;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final HttpClient httpClient;
    private final HttpRequestFactory httpRequestFactory;

    @Override
    public void add(Contact c) {

        ContactsResponse.ContactRequest req = new ContactsResponse.ContactRequest();
        req.setName(c.getName());
        req.setValue(c.getValue());
        req.setType(c.getType().toString().toLowerCase());
        try {
            HttpRequest request = httpRequestFactory.createPostRequestAuth(req, baseUri + "/contacts/add", userService);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            StatusResponse statusResponse = objectMapper.readValue(response.body(), StatusResponse.class);
            if (statusResponse.getStatus().equals("error")) {
                throw new RuntimeException("Error Operation addContact: " + statusResponse.getError());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public List<Contact> findByName(String name) {
        ContactsResponse.ContactRequest req = new ContactsResponse.ContactRequest();
        req.setName(name);
        try {
            HttpRequest request = httpRequestFactory.createPostRequestAuth(req, baseUri + "/contacts/find", userService);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ContactsResponse contactsResponse = objectMapper.readValue(response.body(), ContactsResponse.class);
            if (contactsResponse.getStatus().equals("error")) {
                throw new RuntimeException("Operation error: " + contactsResponse.getError());
            }
            return getCollectContacts(contactsResponse);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Contact> findByValue(String value) {
        ContactsResponse.ContactRequest req = new ContactsResponse.ContactRequest();
        req.setValue(value);
        try {
            HttpRequest request = httpRequestFactory.createPostRequestAuth(req, baseUri + "/contacts/find", userService);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ContactsResponse contactsResponse = objectMapper.readValue(response.body(), ContactsResponse.class);
            if (contactsResponse.getStatus().equals("error")) {
                throw new RuntimeException("Operation error: " + contactsResponse.getError());
            }
            return getCollectContacts(contactsResponse);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Contact> getAll() {
        try {
            HttpRequest request = httpRequestFactory.createGetRequestAuth(baseUri + "/contacts", userService);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ContactsResponse contactsResponse = objectMapper.readValue(response.body(), ContactsResponse.class);
            return getCollectContacts(contactsResponse);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<Contact> getCollectContacts(ContactsResponse contactsResponse) {
        return contactsResponse.getContacts().stream().map(c -> {
            Contact contact = new Contact();
            contact.setName(c.getName());
            contact.setType(ContactType.valueOf(c.getType().toUpperCase()));
            contact.setValue(c.getValue());
            return contact;
        })
                .collect(Collectors.toList());
    }
}
