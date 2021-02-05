package com.k7.menuaction;

import com.k7.services.ContactService;
import com.k7.services.UserService;
import com.k7.utility.OutputContacts;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class SearchByValueContactsMenuAction implements MenuAction {
    private Scanner sc;
    private ContactService contactService;
    private OutputContacts outputContacts;
    private UserService userService;

    @Override
    public void doAction() {
        System.out.println("Enter the start of contact's value please");
        String search = sc.nextLine();
        outputContacts.contactPrint(contactService.findByValue(search));
    }

    @Override
    public String getName() {
        return "Search contact by value";
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
