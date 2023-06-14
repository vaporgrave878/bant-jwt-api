package com.bank.jwtapi.bankjwtapi.сontrollers;

import com.bank.jwtapi.bankjwtapi.dto.UserDto;
import com.bank.jwtapi.bankjwtapi.models.Role;
import com.bank.jwtapi.bankjwtapi.models.User;
import com.bank.jwtapi.bankjwtapi.repos.UserRepository;
import com.bank.jwtapi.bankjwtapi.service.SuperAdminService;
import com.bank.jwtapi.bankjwtapi.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/super_admin")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return superAdminService.users();
    }

    @GetMapping("/admins")
    public List<User> getAdmins() {
        return superAdminService.admins();
    }

    @GetMapping("/clients")
    public List<User> getClients() {
        return superAdminService.clients();
    }

    @PostMapping("/register_admin")
    public User createAdmin(@RequestBody UserDto userDto) {
        return superAdminService.crateAdmin(userDto);
    }


}
