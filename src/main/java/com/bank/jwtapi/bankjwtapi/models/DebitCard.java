package com.bank.jwtapi.bankjwtapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "no-front-bank-cards")
@Data
@ToString(exclude = {"user"})
public class DebitCard{
    private String id,
            number,
            endDate,
            cvv;
    private int balance;

    @DBRef
    @JsonIgnore
    private User user;


}
