package com.bank.jwtapi.bankjwtapi.сontrollers;

import com.bank.jwtapi.bankjwtapi.models.Role;
import com.bank.jwtapi.bankjwtapi.models.User;
import com.bank.jwtapi.bankjwtapi.repos.UserRepository;
import com.bank.jwtapi.bankjwtapi.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/super_admin")
public class SuperAdminController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    public SuperAdminController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<User>> getAdmins() {
        List<User> users = getUsersByRole(Role.ADMIN);
//        Role role = Role.ADMIN;
//        List<Role> roles = new ArrayList<>();
//        roles.add(role);
//        List<User> users = userRepository.findAllByRoles(roles);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<User>> getClients() {
        List<User> users = getUsersByRole(Role.CLIENT);
//        Role role = Role.CLIENT;
//        List<Role> roles = new ArrayList<>();
//        roles.add(role);
//        List<User> users = userRepository.findAllByRoles(roles);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

//    @PostMapping("/{email}/set_admin")
//    public ResponseEntity<User> changeRoleToAdmin(@PathVariable String email) {
//        User user = userRepository.findByEmail(email);
//        if (!user.getRoles().contains(Role.SUPERADMIN)){
//            Role newRole = Role.ADMIN;
//            List<Role> userRoles = new ArrayList<>();
//            userRoles.add(newRole);
//            user.setRoles(userRoles);
//            userRepository.save(user);
//        }
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

    @PostMapping("/register_admin")
    public ResponseEntity<User> createAdmin(@RequestBody User user) {
        User newAdmin = userService.registerAdmin(user);
        return new ResponseEntity<>(newAdmin, HttpStatus.OK);
    }

    private List<User> getUsersByRole(Role role){
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        return userRepository.findAllByRoles(roles);
    }

}
