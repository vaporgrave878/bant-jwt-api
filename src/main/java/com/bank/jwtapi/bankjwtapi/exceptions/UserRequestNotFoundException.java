package com.bank.jwtapi.bankjwtapi.exceptions;

public class UserRequestNotFoundException extends Throwable{
    public UserRequestNotFoundException(String message) {
        super(message);
    }
}
