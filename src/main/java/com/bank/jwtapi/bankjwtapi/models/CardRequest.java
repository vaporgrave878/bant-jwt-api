package com.bank.jwtapi.bankjwtapi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "no-front-bank-card-requests")
public class CardRequest {

    @Id
    private String id;

    private DebitCard card;

    private String email;

    private String code;

    private int sum;
}
