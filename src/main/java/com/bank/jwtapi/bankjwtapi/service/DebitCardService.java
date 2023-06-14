package com.bank.jwtapi.bankjwtapi.service;

import com.bank.jwtapi.bankjwtapi.dto.DebitCardDto;
import com.bank.jwtapi.bankjwtapi.exceptions.CardNotFoundException;
import com.bank.jwtapi.bankjwtapi.exceptions.UserNotFoundException;
import com.bank.jwtapi.bankjwtapi.models.DebitCard;

import java.util.List;

public interface DebitCardService {
    void withdrawFromCard(DebitCardDto paymentDto, String userId) throws Exception, CardNotFoundException;
    DebitCard addCard(String userId) throws UserNotFoundException;
    List<DebitCard> userCardsList(String userId) throws UserNotFoundException;
    DebitCard userCard(String number, String userId) throws UserNotFoundException, CardNotFoundException;
    Double changeBalance(DebitCardDto debitCardDto, String userId) throws UserNotFoundException, CardNotFoundException;
    DebitCard upBalance(String userId, String cardNumber) throws CardNotFoundException;

}
