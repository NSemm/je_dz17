package com.k7.services;

import com.k7.servicefactory.ApiServiceFactory;
import com.k7.servicefactory.FileServiceFactory;
import com.k7.servicefactory.InMemoryServiceFactory;
import com.k7.servicefactory.ServiceFactory;
import com.k7.utility.Menu;
import com.k7.configuration.AppProperties;
import com.k7.menuaction.*;
import com.k7.utility.OutputContacts;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class SetEnvironmentService {
    private AppProperties properties;

    public void start() {
        Scanner sc = new Scanner(System.in);
        OutputContacts outputContacts = new OutputContacts();
        String workmode = properties.getWorkmode();
        ServiceFactory serviceFactory;
        boolean envIsValid = false;
        if (workmode.equals("api")) {
            serviceFactory = new ApiServiceFactory(properties);
            UserService userService = serviceFactory.createUserServices();
            ContactService contactService = serviceFactory.createContactServices();
            bodyExecute(sc, outputContacts, userService, contactService);
            envIsValid = true;
        }
        if (workmode.equals("file")) {
            serviceFactory = new FileServiceFactory(properties);
            UserService userService = serviceFactory.createUserServices();
            ContactService contactService = serviceFactory.createContactServices();
            bodyExecute(sc, outputContacts, userService, contactService);
            envIsValid = true;
        }
        if (workmode.equals("memory")) {
            serviceFactory = new InMemoryServiceFactory();
            UserService userService = serviceFactory.createUserServices();
            ContactService contactService = serviceFactory.createContactServices();
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

}
