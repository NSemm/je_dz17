package com.k7.menuaction;

import com.k7.contacts.Contact;
import com.k7.contacts.ContactType;
import com.k7.services.ContactService;
import com.k7.services.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddContactMenuAction implements MenuAction {
    private ContactService contactService;
    private UserService userService;

    @Override
    public void doAction() {
        Contact contact = new Contact();
        contact.setName("Vasya");
        contact.setType(ContactType.EMAIL);
        contact.setValue("vasya.vorobey@gmail.com");
        contactService.add(contact);
    }

    @Override
    public String getName() {
        return "Add contact";
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
