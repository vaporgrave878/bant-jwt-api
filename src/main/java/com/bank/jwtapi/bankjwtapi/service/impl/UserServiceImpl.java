package com.bank.jwtapi.bankjwtapi.service.impl;

import com.bank.jwtapi.bankjwtapi.models.Role;
import com.bank.jwtapi.bankjwtapi.models.Status;
import com.bank.jwtapi.bankjwtapi.models.User;

import com.bank.jwtapi.bankjwtapi.repos.UserRepository;
import com.bank.jwtapi.bankjwtapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User register(User user) {
//        Role role = new Role();
//        role.setName("SUPER_ADMIN");
//        Role userRole = roleRepository.save(role);
        Role userRole = Role.CLIENT;
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);
        Status userStatus = Status.ACTIVE;
        List<Status> userStatuses = new ArrayList<>();
        userStatuses.add(userStatus);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatuses(userStatuses);

        return userRepository.save(user);
    }

    @Override
    public User registerAdmin(User user) {
        Role userRole = Role.ADMIN;
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);
        Status userStatus = Status.ACTIVE;
        List<Status> userStatuses = new ArrayList<>();
        userStatuses.add(userStatus);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatuses(userStatuses);

        return userRepository.save(user);
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }



    @Override
    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }
}
