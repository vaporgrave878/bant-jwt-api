package com.bank.jwtapi.bankjwtapi.exceptions;

public class LoanNotFoundException extends Exception{

    public LoanNotFoundException(String message) {
        super(message);
    }
}
