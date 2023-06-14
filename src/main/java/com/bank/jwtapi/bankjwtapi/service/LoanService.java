package com.bank.jwtapi.bankjwtapi.service;

import com.bank.jwtapi.bankjwtapi.dto.LoanDto;
import com.bank.jwtapi.bankjwtapi.exceptions.*;
import com.bank.jwtapi.bankjwtapi.models.Loan;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtUser;

import java.util.List;

public interface LoanService {

    void createLoan(String id, LoanDto loanDto) throws UserNotFoundException;

    Loan getLoan(String loanId) throws LoanNotFoundException;

    Loan getLoanForAdmin(String userId, String loanId);

    List<Loan> getLoans(String userId);

    List<Loan> getLoansForAdmin(JwtUser jwtUser, String userId) throws InvalidRoleException;

    Loan payLoan(String cardNumber, String creditId) throws PaymentException, CardNotFoundException;


}
