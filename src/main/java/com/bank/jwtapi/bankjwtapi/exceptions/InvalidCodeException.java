package com.bank.jwtapi.bankjwtapi.exceptions;

public class InvalidCodeException extends Exception{
    public InvalidCodeException(String message) {
        super(message);
    }
}
