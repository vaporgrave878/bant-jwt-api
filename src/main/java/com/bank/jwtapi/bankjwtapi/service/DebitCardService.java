package com.bank.jwtapi.bankjwtapi.service;

import com.bank.jwtapi.bankjwtapi.models.DebitCard;

public interface DebitCardService {
    void withdrawFromCard(DebitCard card, int sum);
}
