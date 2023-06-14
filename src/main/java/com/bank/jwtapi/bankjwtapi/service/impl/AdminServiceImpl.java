package com.bank.jwtapi.bankjwtapi.service.impl;

import com.bank.jwtapi.bankjwtapi.exceptions.CardNotFoundException;
import com.bank.jwtapi.bankjwtapi.exceptions.UserNotFoundException;
import com.bank.jwtapi.bankjwtapi.models.DebitCard;
import com.bank.jwtapi.bankjwtapi.models.Loan;
import com.bank.jwtapi.bankjwtapi.models.Role;
import com.bank.jwtapi.bankjwtapi.models.User;
import com.bank.jwtapi.bankjwtapi.service.AdminService;
import com.bank.jwtapi.bankjwtapi.service.DebitCardService;
import com.bank.jwtapi.bankjwtapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final DebitCardService debitCardService;

    @Override
    public User getUserInfo(String userEmail) throws UserNotFoundException {
        return userService.getUser(userEmail);
    }

    @Override
    public List<User> getClients() {
        return userService.getAllByRole(Role.CLIENT);
    }

    //TODO:later
    //start
    @Override
    public List<DebitCard> getUserCards(String userId) throws UserNotFoundException {
        return debitCardService.userCardsList(userId);
    }

    @Override
    public DebitCard getUserCard(String userId, String cardNumber) throws UserNotFoundException, CardNotFoundException {
        return debitCardService.userCard(cardNumber, userId);
    }

    @Override
    public List<Loan> getUserLoans(String userEmail) {
        return null;
    }

    @Override
    public Loan getUserLoan(String userEmail, String loanId) {
        return null;
    }

    @Override
    public void addCardToUser(String userId) throws UserNotFoundException {
        debitCardService.addCard(userId);
    }

    @Override
    public void addLoanToUser(String userEmail) {
    }
    //end
}
