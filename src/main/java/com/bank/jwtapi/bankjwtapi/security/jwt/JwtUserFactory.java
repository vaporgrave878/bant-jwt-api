package com.bank.jwtapi.bankjwtapi.security.jwt;

import com.bank.jwtapi.bankjwtapi.models.Role;
import com.bank.jwtapi.bankjwtapi.models.Status;
import com.bank.jwtapi.bankjwtapi.models.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    private static Long idCount = 0L;


    public static JwtUser create(User user){
        return new JwtUser(
                idCount++,
                user.getPassword(),
                user.getEmail(),
                user.getName(),
                user.getStatuses().contains(Status.ACTIVE),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }


    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority("ROLE_" + role.toString())
                ).collect(Collectors.toList());
//        return List.of(new SimpleGrantedAuthority(user.getROle))
    }
}

