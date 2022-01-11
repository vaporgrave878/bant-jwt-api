package com.bank.jwtapi.bankjwtapi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


//@Data
public enum Role {

//    @Id
//    private String id;
//    private String name;
//
//    @DBRef
//    private List<User> users;


    CLIENT,//("ROLE_CLIENT"),
    ADMIN,//("ROLE_ADMIN"),
    SUPERADMIN//("ROLE_SUPERADMIN");

//    private String name;
//
//    Role(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }
}
