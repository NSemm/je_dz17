package com.k7.menuaction;

import com.k7.contacts.User;
import com.k7.services.UserService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class RegistrationUserMenuAction implements MenuAction {

    private UserService userService;

    @Override
    public void doAction() {
        User user = new User();
        user.setLogin("Antuan");
        user.setDateBorn(LocalDate.now());
        user.setPassword("9999");
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
