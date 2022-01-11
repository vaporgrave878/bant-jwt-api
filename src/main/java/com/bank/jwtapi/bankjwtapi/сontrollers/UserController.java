package com.bank.jwtapi.bankjwtapi.сontrollers;

import com.bank.jwtapi.bankjwtapi.dto.AuthenticationRequestDto;
import com.bank.jwtapi.bankjwtapi.dto.UserDto;
import com.bank.jwtapi.bankjwtapi.models.*;
import com.bank.jwtapi.bankjwtapi.repos.DebitCardRepository;
import com.bank.jwtapi.bankjwtapi.repos.UserRepository;
import com.bank.jwtapi.bankjwtapi.repos.UserRequestRepository;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtTokenProvider;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtUser;
import com.bank.jwtapi.bankjwtapi.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final UserRequestRepository userRequestRepository;
    private final DebitCardRepository debitCardRepository;

    public UserController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserServiceImpl userService, UserRepository userRepository, UserRequestRepository userRequestRepository, DebitCardRepository debitCardRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.userRepository = userRepository;
        this.userRequestRepository = userRequestRepository;
        this.debitCardRepository = debitCardRepository;
    }

    @PostMapping("/register")
    public void addUser(@RequestBody User user) {
        userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity<UserDto> getUserByEmail() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = jwtUser.getEmail();
        User user = userRepository.findByEmail(email);

        if (user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/add_card")
    public void addCard() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = jwtUser.getEmail();
        User user = userRepository.findByEmail(email);
        UserRequest userRequest = new UserRequest("Add a card", user.getEmail(), Status.ACTIVE);
        userRequestRepository.save(userRequest);
    }

    @PostMapping("/add_loan")
    public void addLoan() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = jwtUser.getEmail();
        User user = userRepository.findByEmail(email);
        UserRequest userRequest = new UserRequest("Add a loan", user.getEmail(), Status.ACTIVE);
        userRequestRepository.save(userRequest);
    }

    @GetMapping("/my_cards")
    public ResponseEntity<List<DebitCard>> myCards() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = jwtUser.getEmail();
        User user = userRepository.findByEmail(email);
        List<DebitCard> cards = user.getDebitCards();
        if (cards == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/my_loans")
    public ResponseEntity<List<Loan>> myLoans() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = jwtUser.getEmail();
        User user = userRepository.findByEmail(email);
        List<Loan> loans = user.getLoans();
        if (loans == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @PostMapping("/user/change_balance")
    public ResponseEntity<DebitCard> changeBalance(@RequestParam String number, @RequestParam int sum, @RequestParam String action) {
        DebitCard card = debitCardRepository.findByNumber(number);
        int balance = card.getBalance();
        if (action.equals("add"))
            balance += sum;
        else if (action.equals("subtract"))
            balance -= sum;
        card.setBalance(balance);
        debitCardRepository.save(card);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }
}
