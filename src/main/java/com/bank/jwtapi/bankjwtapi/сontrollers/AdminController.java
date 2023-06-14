package com.bank.jwtapi.bankjwtapi.сontrollers;

import com.bank.jwtapi.bankjwtapi.exceptions.UserRequestNotFoundException;
import com.bank.jwtapi.bankjwtapi.models.*;
import com.bank.jwtapi.bankjwtapi.repos.DebitCardRepository;
import com.bank.jwtapi.bankjwtapi.repos.LoanRepository;
import com.bank.jwtapi.bankjwtapi.repos.UserRepository;
import com.bank.jwtapi.bankjwtapi.repos.UserRequestRepository;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAllByRole(Role.CLIENT);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
