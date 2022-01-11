package com.bank.jwtapi.bankjwtapi.service;

import com.bank.jwtapi.bankjwtapi.models.User;

import java.util.List;

public interface UserService {
    User register(User user);

    User registerAdmin(User user);

    User findById(String id);

    List<User> getAll();

    User findByUsername(String email);

    void delete(String email);

}

