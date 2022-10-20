package com.bank.jwtapi.bankjwtapi.repos;

import com.bank.jwtapi.bankjwtapi.models.DebitCard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DebitCardRepository extends MongoRepository<DebitCard, String> {
    DebitCard findByNumber(String number);

    DebitCard findByNumberAndCvvAndEndDate(String number, String cvv, String endDate);

    boolean existsByNumberAndCvvAndEndDate(String number, String cvv, String endDate);
}
