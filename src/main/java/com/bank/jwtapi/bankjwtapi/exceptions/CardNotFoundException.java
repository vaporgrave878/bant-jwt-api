package com.bank.jwtapi.bankjwtapi.exceptions;

public class CardNotFoundException extends Throwable{
    public CardNotFoundException(String message) {
        super(message);
    }
}
