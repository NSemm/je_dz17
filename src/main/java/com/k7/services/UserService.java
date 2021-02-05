package com.k7.services;

import com.k7.contacts.User;

import java.util.List;

public interface UserService {
    String getToken();
    void registration(User user);
    void login(User user);
    List<User> getAll();
    boolean isAuth();
}
