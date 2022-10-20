package com.bank.jwtapi.bankjwtapi.repos;

import com.bank.jwtapi.bankjwtapi.models.CardRequest;
import com.bank.jwtapi.bankjwtapi.models.DebitCard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardRequestRepository extends MongoRepository<CardRequest, String> {
//    DebitCard findByCard(DebitCard debitCard);

    CardRequest findByCard(DebitCard debitCard);

    CardRequest findByCode(String code);

    boolean existsByCode(String code);
}
