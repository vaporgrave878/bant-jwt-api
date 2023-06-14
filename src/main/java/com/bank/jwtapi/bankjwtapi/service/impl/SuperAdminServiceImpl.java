package com.bank.jwtapi.bankjwtapi.service.impl;

import com.bank.jwtapi.bankjwtapi.dto.UserDto;
import com.bank.jwtapi.bankjwtapi.models.Role;
import com.bank.jwtapi.bankjwtapi.models.User;
import com.bank.jwtapi.bankjwtapi.repos.UserRepository;
import com.bank.jwtapi.bankjwtapi.service.SuperAdminService;
import com.bank.jwtapi.bankjwtapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final UserRepository userRepository;
    private final UserService userService;


    @Override
    public List<User> users() {
        return userRepository.findAll().stream().filter(user -> !user.getRole().equals(Role.SUPERADMIN)).collect(Collectors.toList());
    }

    @Override
    public List<User> admins() {
        return userRepository.findAllByRole(Role.ADMIN);
    }

    @Override
    public List<User> clients() {
        return userRepository.findAllByRole(Role.CLIENT);
    }

    @Override
    public User crateAdmin(UserDto userDto) {
        return userService.registerAdmin(userDto);
    }
}
