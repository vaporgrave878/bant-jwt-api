package com.bank.jwtapi.bankjwtapi.сontrollers;

import com.bank.jwtapi.bankjwtapi.exceptions.UserRequestNotFoundException;
import com.bank.jwtapi.bankjwtapi.models.*;
import com.bank.jwtapi.bankjwtapi.repos.DebitCardRepository;
import com.bank.jwtapi.bankjwtapi.repos.LoanRepository;
import com.bank.jwtapi.bankjwtapi.repos.UserRepository;
import com.bank.jwtapi.bankjwtapi.repos.UserRequestRepository;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final UserRequestRepository userRequestRepository;
    private final DebitCardRepository debitCardRepository;
    private final LoanRepository loanRepository;

    public AdminController(UserRepository userRepository, UserRequestRepository userRequestRepository, DebitCardRepository debitCardRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.userRequestRepository = userRequestRepository;
        this.debitCardRepository = debitCardRepository;
        this.loanRepository = loanRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        Role role = Role.CLIENT;
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        List<User> users = userRepository.findAllByRoles(roles);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{email}/user_cards")
    public ResponseEntity<List<DebitCard>> getUserCards(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        List<DebitCard> cards = user.getDebitCards();
        if (cards == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/{email}/user_loans")
    public ResponseEntity<List<Loan>> getUserLoans(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        List<Loan> loans = user.getLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<UserRequest>> seeRequests() {
        List<UserRequest> requests = userRequestRepository.findAll();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PostMapping("/{id}/add_card_to_user")
    public void addCardToUser(@PathVariable String id, @RequestBody DebitCard card) throws UserRequestNotFoundException {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserRequest> userRequestById = Optional.ofNullable(userRequestRepository.findById(id)
                .orElseThrow(() -> new UserRequestNotFoundException("Request with id " + id + " not found ")));
        if (!userRequestById.get().getStatus().equals(Status.NOT_ACTIVE)) {
            String email = userRequestById.get().getUserEmail();
            User requestingUser = userRepository.findByEmail(email);
            card.setUser(requestingUser);
            debitCardRepository.save(card);
            List<DebitCard> cards = requestingUser.getDebitCards();
            cards.add(card);
            requestingUser.setDebitCards(cards);
            userRepository.save(requestingUser);
            UserRequest userRequest = userRequestRepository.findByIdAndUserEmail(id, email);
            userRequest.setStatus(Status.NOT_ACTIVE);
            userRequest.setAdminClosed(user.getEmail());
            userRequestRepository.save(userRequest);
        }

    }

    @PostMapping("/{id}/add_loan_to_user")
    public void addLoanToUser(@PathVariable String id, @RequestBody Loan loan) throws UserRequestNotFoundException {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserRequest> userRequestById = Optional.ofNullable(userRequestRepository.findById(id)
                .orElseThrow(() -> new UserRequestNotFoundException("Request with id " + id + " not found ")));
        String email = userRequestById.get().getUserEmail();
        loan.setStatus(Status.ACTIVE);
        if (!userRequestById.get().getStatus().equals(Status.NOT_ACTIVE)) {
            User requestingUser = userRepository.findByEmail(email);

            List<Loan> loans = requestingUser.getLoans();

            int _id = loans.size();
            if (loans.isEmpty() || loans == null) {
                loans = new ArrayList<>();
                _id = 1;
            }


            loan.setCreditId(requestingUser.getEmail() + "_" + _id);
            loan.setUser(requestingUser);
            loans.add(loan);

            requestingUser.setLoans(loans);
            loanRepository.save(loan);
            userRepository.save(requestingUser);

            UserRequest userRequest = userRequestRepository.findByIdAndUserEmail(id, email);
            userRequest.setStatus(Status.NOT_ACTIVE);
            userRequest.setAdminClosed(user.getEmail());
            userRequestRepository.save(userRequest);
        }
    }
}
