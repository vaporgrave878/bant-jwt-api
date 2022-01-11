package com.bank.jwtapi.bankjwtapi.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "no-front-bank-loans")
@Data
public class Loan {
    private String id,
        date;
    private int sum,
            perc,
            period,
            monthlyPayment;

    @DBRef
    private User user;
}
