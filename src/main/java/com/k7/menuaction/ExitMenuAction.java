package com.k7.menuaction;

import com.k7.services.UserService;

public class ExitMenuAction implements MenuAction {
    private UserService userService;

    @Override
    public void doAction() {
        System.out.println("Selected " + getName() + " action");
    }

    @Override
    public String getName() {
        return "Exit";
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public boolean closeAfter() {
        return true;
    }
}
