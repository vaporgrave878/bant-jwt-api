package com.bank.jwtapi.bankjwtapi.service.impl;

import com.bank.jwtapi.bankjwtapi.dto.LoanDto;
import com.bank.jwtapi.bankjwtapi.exceptions.*;
import com.bank.jwtapi.bankjwtapi.models.*;
import com.bank.jwtapi.bankjwtapi.repos.DebitCardRepository;
import com.bank.jwtapi.bankjwtapi.repos.LoanRepository;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtUser;
import com.bank.jwtapi.bankjwtapi.service.LoanService;
import com.bank.jwtapi.bankjwtapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final UserService userService;
    private final LoanRepository loanRepository;
    private final DebitCardRepository debitCardRepository;

    @Override
    public void createLoan(String id, LoanDto loanDto) throws UserNotFoundException {
        User user = userService.findById(id);
        loanRepository.save(Loan.builder()
                .date(loanDto.getDate())
                .sum(loanDto.getSum())
                .perc(loanDto.getPerc())
                .period(loanDto.getPeriod())
                .monthlyPayment(loanDto.getMonthlyPayment())
                .sumToClose(loanDto.getSum())
                .alreadyPayed(0)
                .creditId(user.getEmail() + "_" + loanRepository.countByUser(user))
                .user(user)
                .status(Status.ACTIVE)
                .build());
    }

    @Override
    public Loan getLoan(String loanId) throws LoanNotFoundException {
        return loanRepository.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan with id: " + loanId + " not found"));
    }

    @Override
    public Loan getLoanForAdmin(String userId, String loanId) {
        return loanRepository.findByIdAndUser_Id(loanId, userId);
    }

    @Override
    public List<Loan> getLoans(String userId) {
        return loanRepository.findAllByUser_Id(userId);
    }

    @Override
    public List<Loan> getLoansForAdmin(JwtUser jwtUser, String userId) throws InvalidRoleException {
        if (jwtUser.getAuthorities().contains(Role.ADMIN.name())) {
            throw new InvalidRoleException("This user is not an admin");
        }
        return loanRepository.findAllByUser_Id(userId);
    }

    @Override
    public Loan payLoan(String cardNumber, String creditId) throws PaymentException, CardNotFoundException {

        Loan loan = loanRepository.findByCreditId(creditId);
        DebitCard card = debitCardRepository.findByNumber(cardNumber).orElseThrow(() -> new CardNotFoundException("Card not found"));

        Double balance = card.getBalance();
        if (balance <= 0 || loan.getStatus().equals(Status.NOT_ACTIVE)) {
            throw new PaymentException("Cannot pay loan");
        }

        int sum = loan.getMonthlyPayment();
        int payment = (int) (balance - sum);
        card.setBalance((double) payment);
        int alreadyPayed = loan.getAlreadyPayed();
        loan.setAlreadyPayed(alreadyPayed + sum);

        if (loan.getAlreadyPayed() >= loan.getSumToClose()) {
            loan.setStatus(Status.NOT_ACTIVE);
        }

        loanRepository.save(loan);
        debitCardRepository.save(card);

        return loan;
    }
}
