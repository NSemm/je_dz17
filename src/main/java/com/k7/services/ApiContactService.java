package com.k7.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.k7.contacts.Contact;
import com.k7.contacts.ContactType;
import com.k7.dto.ContactsResponse;
import com.k7.dto.StatusResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.URI;
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
    private HttpClient httpClient;

    @Override
    public void add(Contact c) {

        ContactsResponse.ContactRequest req = new ContactsResponse.ContactRequest();
        req.setName(c.getName());
        req.setValue(c.getValue());
        req.setType(c.getType().toString().toLowerCase());
        try {
            HttpRequest request = requestBuild(req, "contacts/add");
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println("add_resp" + response.body());
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
            HttpRequest request = requestBuild(req, "contacts/find");
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println("contBname:" + response.body());
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
            HttpRequest request = requestBuild(req, "contacts/find");
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
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUri + "contacts"))
                    .GET()
                    .header("Accept", "Application/json")
                    .header("Content-Type", "Application/json")
                    .header("Authorization", "Bearer " + userService.getToken())
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println("contAll:"+response.body());
            ContactsResponse contactsResponse = objectMapper.readValue(response.body(), ContactsResponse.class);
            return getCollectContacts(contactsResponse);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private HttpRequest requestBuild(Object req, String uri) throws JsonProcessingException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUri + uri))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(req)))
                .header("Authorization", "Bearer " + userService.getToken())
                .header("Accept", "Application/json")
                .header("Content-Type", "Application/json")
                .build();
        return request;
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