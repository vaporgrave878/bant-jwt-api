package com.bank.jwtapi.bankjwtapi.service;

import javax.mail.MessagingException;

public interface CardRequestService {
    void sendVerificationCode(String email, String code) throws MessagingException;
}
