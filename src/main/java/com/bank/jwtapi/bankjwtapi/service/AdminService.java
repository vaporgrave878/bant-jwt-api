package com.bank.jwtapi.bankjwtapi.service;

import com.bank.jwtapi.bankjwtapi.exceptions.CardNotFoundException;
import com.bank.jwtapi.bankjwtapi.exceptions.UserNotFoundException;
import com.bank.jwtapi.bankjwtapi.models.DebitCard;
import com.bank.jwtapi.bankjwtapi.models.Loan;
import com.bank.jwtapi.bankjwtapi.models.User;

import java.util.List;

public interface AdminService {
    User getUserInfo(String userEmail) throws UserNotFoundException;
    List<User> getClients();
    List<DebitCard> getUserCards(String userId) throws UserNotFoundException;
    DebitCard getUserCard(String userId, String cardNumber) throws UserNotFoundException, CardNotFoundException;
    List<Loan> getUserLoans(String userEmail);
    Loan getUserLoan(String userEmail, String loanId);
    void addCardToUser(String userEmail) throws UserNotFoundException;
    void addLoanToUser(String userEmail);

}
