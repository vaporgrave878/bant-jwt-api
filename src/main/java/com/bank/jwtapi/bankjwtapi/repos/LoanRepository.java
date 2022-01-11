package com.bank.jwtapi.bankjwtapi.repos;

import com.bank.jwtapi.bankjwtapi.models.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanRepository extends MongoRepository<Loan, String> {
}
