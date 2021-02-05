package com.k7.services;

import com.k7.contacts.Contact;

import java.util.List;

public interface ContactService {
    void add(Contact c);
    void remove(Integer id);
    List<Contact> findByName(String name);
    List<Contact> findByValue(String value);
    List<Contact> getAll();
}
