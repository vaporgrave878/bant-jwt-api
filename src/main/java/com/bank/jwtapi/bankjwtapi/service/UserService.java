package com.bank.jwtapi.bankjwtapi.service;

import com.bank.jwtapi.bankjwtapi.dto.AuthenticationRequestDto;
import com.bank.jwtapi.bankjwtapi.dto.UserDto;
import com.bank.jwtapi.bankjwtapi.exceptions.UserNotFoundException;
import com.bank.jwtapi.bankjwtapi.exceptions.UserNotRelatedException;
import com.bank.jwtapi.bankjwtapi.models.Role;
import com.bank.jwtapi.bankjwtapi.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    void register(UserDto userDto);

//    void sendVerificationEmail(JwtUser jwtUser) throws MessagingException;

    User registerAdmin(UserDto userDto);

    User findById(String id) throws UserNotFoundException;
    User getUser(String Email) throws UserNotFoundException;

    List<User> getAll();

    List<User> getAllByRole(Role role);


//    User findByUsername(String email);

    void delete(String email, String id) throws UserNotFoundException, UserNotRelatedException;

    ResponseEntity<Map<String, String>> login(AuthenticationRequestDto authenticationRequestDto);

}

