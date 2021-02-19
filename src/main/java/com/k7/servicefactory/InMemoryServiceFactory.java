package com.k7.servicefactory;

import com.k7.services.*;

public class InMemoryServiceFactory implements ServiceFactory {

    @Override
    public ContactService createContactServices() {
        ContactService contactService = new InMemoryContactService();
        return contactService;
    }

    @Override
    public UserService createUserServices() {
        UserService userService = new MocksUserService();
        return userService;
    }
}
