package com.k7.contacts;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private String login;
    private LocalDate dateBorn;
    private String password;
}
