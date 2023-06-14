package com.bank.jwtapi.bankjwtapi.repos;

import com.bank.jwtapi.bankjwtapi.models.Loan;
import com.bank.jwtapi.bankjwtapi.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LoanRepository extends MongoRepository<Loan, String> {

    Loan findByCreditId(String creditId);

    int countByUser(User user);

    Loan findByIdAndUser_Id(String loanId, String userId);

    List<Loan> findAllByUser_Id(String userId);


}
