package com.k7.utility;

import com.k7.contacts.Contact;

import java.util.List;

public class OutputContacts {
    public void contactPrint(List<Contact> list) {
        System.out.println("------Contact list------");
        for (Contact c : list) {
            System.out.println(c.getName() + " [" + c.getType().toString().toLowerCase() + ":" + c.getValue() + "]");
        }
        System.out.println("------------------------");
    }
}
