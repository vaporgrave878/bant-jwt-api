package com.bank.jwtapi.bankjwtapi.service;

import com.bank.jwtapi.bankjwtapi.dto.UserDto;
import com.bank.jwtapi.bankjwtapi.models.User;

import java.util.List;

public interface SuperAdminService {

    List<User> users();
    List<User> admins();
    List<User> clients();
    User crateAdmin(UserDto userDto);

}
