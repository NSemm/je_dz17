package com.k7.menuaction;

import com.k7.contacts.User;
import com.k7.services.UserService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class LoginUserMenuAction implements MenuAction {
    private UserService userService;

    @Override
    public void doAction() {
//        User user = new User();
//        user.setLogin("NSem");
//        user.setDateBorn(LocalDateTime.now());
//        //user.setPassword("321741");
//        user.setPassword("1111");
//        userService.auth(user);

  //      User user2 = new User();
//        user2.setLogin("semerenko");
//        user2.setDateBorn(LocalDateTime.now());
//        user2.setPassword("321741");
//        userService.auth(user2);

        User user3 = new User();
        user3.setLogin("Antuan");
        user3.setDateBorn(LocalDateTime.now());
        user3.setPassword("9999");
        userService.auth(user3);
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
