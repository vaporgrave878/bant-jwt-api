package com.bank.jwtapi.bankjwtapi.dto;

import com.bank.jwtapi.bankjwtapi.models.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String name;
    private String surname;
    private String birthdate;
    private String sex;
    private String email;
    private String password;
    private List<DebitCard> debitCards;
    private List<Loan> loans;
    private List<Role> roles;
    private List<Status> statuses;


    public User toUser(){
        return new User(
                name,
                surname,
                birthdate,
                sex,
                email,
                password,
                debitCards,
                loans,
                roles,
                statuses
        );
    }

    public static UserDto fromUser(User user){
        return new UserDto(user.getName(),
                user.getSurname(),
                user.getBirthdate(),
                user.getSex(),
                user.getEmail(),
                user.getPassword(),
                user.getDebitCards(),
                user.getLoans(),
                user.getRoles(),
                user.getStatuses());
    }
}
