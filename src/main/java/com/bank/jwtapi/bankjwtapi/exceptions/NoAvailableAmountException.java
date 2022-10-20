package com.bank.jwtapi.bankjwtapi.exceptions;

public class NoAvailableAmountException extends Throwable{
    public NoAvailableAmountException(String message) {
        super(message);
    }
}
