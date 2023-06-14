package com.bank.jwtapi.bankjwtapi.сontrollers;

import com.bank.jwtapi.bankjwtapi.dto.LoanDto;
import com.bank.jwtapi.bankjwtapi.dto.PaymentDto;
import com.bank.jwtapi.bankjwtapi.exceptions.*;
import com.bank.jwtapi.bankjwtapi.models.Loan;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtUser;
import com.bank.jwtapi.bankjwtapi.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/create-loan")
    public void createLoan(JwtUser jwtUser,@RequestBody LoanDto loanDto) throws UserNotFoundException {
        loanService.createLoan(jwtUser.getId(), loanDto);
    }

    @GetMapping ("/get-loan/{loanId}")
    public Loan getLoan(@PathVariable String loanId) throws LoanNotFoundException {
        return loanService.getLoan(loanId);
    }

    @GetMapping("/get-loans")
    public List<Loan> getLoans(JwtUser jwtUser){
        return loanService.getLoans(jwtUser.getId());
    }

    @GetMapping("/loan-for-admin/{userId}")
    public Loan getLoanForAdmin(@PathVariable String userId, @RequestParam String loanId){
        return loanService.getLoanForAdmin(userId, loanId);
    }

    @GetMapping("/loans-for-admin/{userId}")
    public List<Loan> getLoansForAdmin(@PathVariable String userId, JwtUser jwtUser) throws InvalidRoleException {
        return loanService.getLoansForAdmin(jwtUser, userId);
    }

    @PostMapping("/pay-loan")
    public Loan payLoan(@RequestBody PaymentDto paymentDto) throws PaymentException, CardNotFoundException {
        return loanService.payLoan(paymentDto.getCardNumber(), paymentDto.getCreditId());
    }



}
