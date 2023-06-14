package com.bank.jwtapi.bankjwtapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "no-front-bank-loans")
@Data
@Builder
public class Loan {
    private String id;
    private String date;
    private String creditId;
    private int sum;
    private int perc;
    private int period;
    private int monthlyPayment;
    private int sumToClose;
    private int alreadyPayed;
    private Status status;

    @DBRef
    @JsonIgnore
    private User user;


}
