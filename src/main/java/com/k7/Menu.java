package com.k7;

import com.k7.menuaction.MenuAction;

import java.util.Arrays;
import java.util.Scanner;

public class Menu {

    private MenuAction[] actions = new MenuAction[0];
    private Scanner sc;

    public Menu(Scanner sc) {
        this.sc = sc;
    }

    public void addAction(MenuAction action) {
        actions = Arrays.copyOf(actions, actions.length + 1);
        actions[actions.length - 1] = action;
    }

    public void run() {
        boolean flagAction = false;
        if (actions.length < 1) System.out.println("before run execute addAction please!");
        else {
            while (!flagAction) {
                flagAction = runAction(runDialog());
            }
        }
    }

    private int runDialog() {
        System.out.println("Choose action!");
        System.out.println("---------------------------------");
        for (int i = 0; i < actions.length; i++) {
            if (actions[i].isVisible())
                System.out.println(i + 1 + " " + actions[i].getName());
        }
        System.out.println("---------------------------------");
        int idChoose = sc.nextInt();
        sc.nextLine();
        return idChoose;
    }

    private boolean runAction(int idChoose) {
        int index = idChoose - 1;
        actions[index].doAction();
        return actions[index].closeAfter();
    }
}
