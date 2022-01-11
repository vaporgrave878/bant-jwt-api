package com.bank.jwtapi.bankjwtapi.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "no-front-bank-cards")
@Data
public class DebitCard{
    private String id,
            number,
            endDate,
            cvv;
    private int balance;

    @DBRef
    private User user;


}
