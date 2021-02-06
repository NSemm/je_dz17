package com.k7.services;

import com.k7.contacts.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryContactService implements ContactService {
    List<Contact> contactList = new ArrayList<>();

    @Override
    public void add(Contact c) {
        contactList.add(c);
    }

    @Override
    public void remove(Integer id) {
throw new UnsupportedOperationException("in develop");
    }

    @Override
    public List<Contact> findByName(String name) {
        return contactList.stream()
                .filter(c->c.getName().startsWith(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contact> findByValue(String value) {
        return contactList.stream()
                .filter(c->c.getValue().startsWith(value))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contact> getAll() {
        return contactList.stream().collect(Collectors.toList());
    }
}
