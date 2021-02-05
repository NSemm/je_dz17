package com.k7.menuaction;

import com.k7.services.UserService;

public interface MenuAction {
    void doAction();
    String getName();
    boolean isVisible();
    boolean closeAfter();
}
