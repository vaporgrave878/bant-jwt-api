package com.bank.jwtapi.bankjwtapi.service;

import com.bank.jwtapi.bankjwtapi.dto.AuthenticationRequestDto;
import com.bank.jwtapi.bankjwtapi.dto.UserDto;
import com.bank.jwtapi.bankjwtapi.exceptions.UserNotFoundException;
import com.bank.jwtapi.bankjwtapi.models.User;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtUser;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;

public interface UserService {
    void register(UserDto userDto);

    User getUser(String email) throws UserNotFoundException;

//    void sendVerificationEmail(JwtUser jwtUser) throws MessagingException;

    User registerAdmin(User user);

    User findById(String id) throws UserNotFoundException;

    List<User> getAll();

//    User findByUsername(String email);

    void delete(String email);

    ResponseEntity<Map<String, String>> login(AuthenticationRequestDto authenticationRequestDto);

}

