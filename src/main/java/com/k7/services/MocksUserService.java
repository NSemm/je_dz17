package com.k7.services;

import com.k7.contacts.User;

import java.util.Collections;
import java.util.List;

public class MocksUserService implements UserService {
    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void registration(User user) {
        throw new UnsupportedOperationException("registration disable");
    }

    @Override
    public void auth(User user) {
        throw new UnsupportedOperationException("login disable");
    }

    @Override
    public List<User> getAll() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAuth() {
        return true;
    }
}
