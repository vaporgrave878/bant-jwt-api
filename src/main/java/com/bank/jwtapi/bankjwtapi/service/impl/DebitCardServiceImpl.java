package com.bank.jwtapi.bankjwtapi.service.impl;

import com.bank.jwtapi.bankjwtapi.models.CardRequest;
import com.bank.jwtapi.bankjwtapi.models.DebitCard;
import com.bank.jwtapi.bankjwtapi.repos.CardRequestRepository;
import com.bank.jwtapi.bankjwtapi.repos.DebitCardRepository;
import com.bank.jwtapi.bankjwtapi.service.DebitCardService;
import org.springframework.stereotype.Service;

@Service
public class DebitCardServiceImpl implements DebitCardService {

    private final DebitCardRepository debitCardRepository;

    public DebitCardServiceImpl(DebitCardRepository debitCardRepository) {
        this.debitCardRepository = debitCardRepository;
    }

    @Override
    public void withdrawFromCard(DebitCard card, int sum) {
        DebitCard debitCard = debitCardRepository.findByNumber(card.getNumber());
        debitCard.setBalance(debitCard.getBalance() - sum);
        debitCardRepository.save(debitCard);
    }
}
