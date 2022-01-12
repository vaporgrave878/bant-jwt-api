package com.bank.jwtapi.bankjwtapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "no-front-bank-loans")
@Data
public class Loan {
    private String id,
        date,
        creditId;
    private int sum,
            perc,
            period,
            monthlyPayment,
            sumToClose,
            alreadyPayed;


    @DBRef
    @JsonIgnore
    private User user;

    private Status status;
}
