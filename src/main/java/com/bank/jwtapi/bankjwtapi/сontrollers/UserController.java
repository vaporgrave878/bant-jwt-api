package com.bank.jwtapi.bankjwtapi.сontrollers;

import com.bank.jwtapi.bankjwtapi.dto.AuthenticationRequestDto;
import com.bank.jwtapi.bankjwtapi.dto.UserDto;
import com.bank.jwtapi.bankjwtapi.exceptions.UserNotFoundException;
import com.bank.jwtapi.bankjwtapi.models.User;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtUser;
import com.bank.jwtapi.bankjwtapi.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;


    @PostMapping("/register")
    public void addUser(@RequestBody UserDto userDto) {
        userService.register(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequestDto requestDto) {
        return userService.login(requestDto);
    }

//    @GetMapping(value = "/user")
//    public ResponseEntity<UserDto> getUserByEmail() {
//        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String email = jwtUser.getEmail();
//        User user = userRepository.findByEmail(email);
//
//        if (user == null)
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//        UserDto result = UserDto.fromUser(user);
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @GetMapping("/get-account-info")
    public User getUser(JwtUser jwtUser) throws UserNotFoundException {
       return userService.findById(jwtUser.getId());
    }

//    @GetMapping("/send-message")
//    public void sendEmail(JwtUser jwtUser) throws MessagingException {
//        userService.sendVerificationEmail(jwtUser);
//    }


    //TODO: move to service
//    @PostMapping("/add_card")
//    public void addCard() {
//        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String email = jwtUser.getEmail();
//        User user = userRepository.findByEmail(email);
//        UserRequest userRequest = new UserRequest("Add a card", user.getEmail(), Status.ACTIVE);
//        userRequestRepository.save(userRequest);
//    }
//
//    @PostMapping("/add_loan")
//    public void addLoan() {
//        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String email = jwtUser.getEmail();
//        User user = userRepository.findByEmail(email);
//        UserRequest userRequest = new UserRequest("Add a loan", user.getEmail(), Status.ACTIVE);
//        userRequestRepository.save(userRequest);
//    }
//
//    @GetMapping("/my_cards")
//    public ResponseEntity<List<DebitCard>> myCards() {
//        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String email = jwtUser.getEmail();
//        User user = userRepository.findByEmail(email);
//        List<DebitCard> cards = user.getDebitCards();
//        if (cards == null)
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        return new ResponseEntity<>(cards, HttpStatus.OK);
//    }
//
//    @GetMapping("/my_loans")
//    public ResponseEntity<List<Loan>> myLoans() {
//        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String email = jwtUser.getEmail();
//        User user = userRepository.findByEmail(email);
//        List<Loan> loans = user.getLoans();
//        if (loans == null)
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        return new ResponseEntity<>(loans, HttpStatus.OK);
//    }
//
//    @PostMapping("/change_balance")
//    public ResponseEntity<DebitCard> changeBalance(@RequestParam String number, @RequestParam int sum, @RequestParam String action) {
//        DebitCard card = debitCardRepository.findByNumber(number);
//        int balance = card.getBalance();
//        if (action.equals("add"))
//            balance += sum;
//        else if (action.equals("subtract") || balance - sum >= 0)
//            balance -= sum;
//        card.setBalance(balance);
//        debitCardRepository.save(card);
//        return new ResponseEntity<>(card, HttpStatus.OK);
//    }
//
//    @PostMapping("/pay_loan/{creditId}")
//    public ResponseEntity<Loan> payLoan(@RequestParam String number, @PathVariable String creditId) {
//        Loan loan = loanRepository.findByCreditId(creditId);
//        DebitCard card = debitCardRepository.findByNumber(number);
//
//        ResponseEntity<Loan> response = new ResponseEntity<>(loan, HttpStatus.OK);
//        int balance = card.getBalance();
//        if (balance <= 0 || loan.getStatus().equals(Status.NOT_ACTIVE)) {
//            return response;
//        }
//        int sum = loan.getMonthlyPayment();
//        int payment = balance - sum;
//        card.setBalance(payment);
//        int alreadyPayed = loan.getAlreadyPayed();
//        loan.setAlreadyPayed(alreadyPayed + sum);
//        if (loan.getAlreadyPayed() >= loan.getSumToClose())
//            loan.setStatus(Status.NOT_ACTIVE);
//        loanRepository.save(loan);
//        debitCardRepository.save(card);
//        return response;
//    }

}
