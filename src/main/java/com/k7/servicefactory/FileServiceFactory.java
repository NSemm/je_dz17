package com.k7.servicefactory;

import com.k7.configuration.AppProperties;
import com.k7.services.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileServiceFactory implements ServiceFactory {
    private final AppProperties appProperties;


    @Override
    public ContactService createContactServices() {
        ContactService contactService = new NioFileContactService(appProperties.getFilePath());
        return contactService;
    }

    @Override
    public UserService createUserServices() {
        UserService userService = new MocksUserService();
        return userService;
    }
}
