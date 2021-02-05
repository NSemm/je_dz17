package com.k7.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.k7.contacts.User;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private String status;
    private List<User> users;
}
