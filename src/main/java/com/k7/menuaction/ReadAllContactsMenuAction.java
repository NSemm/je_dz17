package com.k7.menuaction;

import com.k7.services.ContactService;
import com.k7.services.UserService;
import com.k7.utility.OutputContacts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReadAllContactsMenuAction implements MenuAction {
    private ContactService contactService;
    private OutputContacts outputContacts;
    private UserService userService;

    @Override
    public void doAction() {
        outputContacts.contactPrint(contactService.getAll());
    }

    @Override
    public String getName() {
        return "Show all contacts";
    }

    @Override
    public boolean isVisible() {
        return userService.isAuth();
    }

    @Override
    public boolean closeAfter() {
        return false;
    }
}
