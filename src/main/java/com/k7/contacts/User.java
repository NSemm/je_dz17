package com.k7.contacts;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class User {
    private String login;
    private LocalDateTime dateBorn;
    private String password;
}
