package com.k7.servicefactory;

import com.k7.services.ContactService;
import com.k7.services.UserService;

public interface ServiceFactory {
    ContactService createContactServices();

    UserService createUserServices();
}
