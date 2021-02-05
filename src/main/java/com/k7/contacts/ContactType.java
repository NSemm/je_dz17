package com.k7.contacts;

public enum ContactType {
    PHONE(1), EMAIL(2);
    int type;

    ContactType(int type) {
        this.type = type;
    }
}
