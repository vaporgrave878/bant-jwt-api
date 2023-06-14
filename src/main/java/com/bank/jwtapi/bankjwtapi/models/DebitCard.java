package com.bank.jwtapi.bankjwtapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "no-front-bank-cards")
@Data
@ToString(exclude = {"user"})
@Builder

public class DebitCard {
    private String id;
    private String number;
    @JsonIgnore
    private String endDate;
    @JsonIgnore
    private String cvv;
    @JsonIgnore
    private Double balance;

    @DBRef
    @JsonIgnore
    private User user;


}
