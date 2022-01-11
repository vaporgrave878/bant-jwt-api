package com.bank.jwtapi.bankjwtapi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "no-front-bank-requests")
public class UserRequest {

    @Id
    private String id;

    private String name;

    private String userEmail;

    private Status status;

    private String adminClosed;

    public UserRequest(String name, String userEmail, Status status) {
        this.name = name;
        this.userEmail = userEmail;
        this.status = status;
    }
}
