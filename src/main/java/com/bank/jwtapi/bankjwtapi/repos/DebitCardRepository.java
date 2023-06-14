package com.bank.jwtapi.bankjwtapi.repos;

import com.bank.jwtapi.bankjwtapi.models.DebitCard;
import com.bank.jwtapi.bankjwtapi.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DebitCardRepository extends MongoRepository<DebitCard, String> {
    Optional<DebitCard> findByNumber(String number);
    Optional<DebitCard> findByNumberAndUserId(String number, String userId);
    Optional<DebitCard> findByNumberAndUser(String number, User user);

    Optional<DebitCard> findByNumberAndCvvAndEndDate(String number, String cvv, String endDate);

    List<DebitCard> findAllByUserId(String userId);
    boolean existsByNumber(String number);
    boolean existsByCvv(String cvv);
    boolean existsByNumberAndCvvAndEndDate(String number, String cvv, String endDate);

    List<DebitCard> findAllByUser(User user);
}
