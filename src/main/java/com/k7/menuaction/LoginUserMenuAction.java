package com.k7.menuaction;

import com.k7.contacts.User;
import com.k7.services.UserService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class LoginUserMenuAction implements MenuAction {
    private UserService userService;

    @Override
    public void doAction() {
        User user = new User();
        user.setLogin("NSem");
        user.setDateBorn(LocalDate.now());
        user.setPassword("321741");
        User user2 = new User();
        user2.setLogin("semerenko");
        user2.setDateBorn(LocalDate.now());
        user2.setPassword("321741");
        userService.login(user);
    }

    @Override
    public String getName() {
        return "Logon";
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
