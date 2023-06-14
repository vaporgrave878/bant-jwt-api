package com.bank.jwtapi.bankjwtapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebitCardDto {
    String cardNumber;
    String sum;
    String action;
}
