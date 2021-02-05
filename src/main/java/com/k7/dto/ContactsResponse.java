package com.k7.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactsResponse {
    private List<ContactRequest> contacts;
    private String status;
    private String error;

    @Data
    public static class ContactRequest {
        private String name;
        private String type;
        private String value;
        private Integer id;
    }

}

