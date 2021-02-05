package com.k7.menuaction;

import com.k7.services.ContactService;
import com.k7.services.UserService;
import com.k7.utility.OutputContacts;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class SearchByNameContactsMenuAction implements MenuAction {
    private Scanner sc;
    private ContactService contactService;
    private OutputContacts outputContacts;
    private UserService userService;

    @Override
    public void doAction() {
        System.out.println("Enter the begining of contact name please");
        String search = sc.nextLine();
        outputContacts.contactPrint(contactService.findByName(search));
    }

    @Override
    public String getName() {
        return "Search contact by name";
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
