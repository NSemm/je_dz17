package com.k7.menuaction;

import com.k7.contacts.User;
import com.k7.services.UserService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class RegistrationUserMenuAction implements MenuAction {

    private UserService userService;

    @Override
    public void doAction() {
        User user = new User();
        user.setLogin("NSem");
        user.setDateBorn(LocalDateTime.now());
        user.setPassword("1111");
        userService.registration(user);
    }

    @Override
    public String getName() {
        return "Registration";
    }

    @Override
    public boolean isVisible() {
        return !userService.isAuth();
    }

    @Override
    public boolean closeAfter() {
        return false;
    }
}
