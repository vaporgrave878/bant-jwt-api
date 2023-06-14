package com.bank.jwtapi.bankjwtapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "no-front-bank-users")
@Builder
public class User {

    @Id
    private String id;
    private String name;
    private String surname;
    private String birthdate;
    private String sex;
    private String email;
    private String password;
//    private List<Role> roles;
    private Role role;
    private List<Status> statuses;


//    public User(String name, String surname, String birthdate, String sex, String email, String password, List<DebitCard> debitCards, List<Loan> loans) {
//        this.name = name;
//        this.surname = surname;
//        this.birthdate = birthdate;
//        this.sex = sex;
//        this.email = email;
//        this.password = password;
//        this.debitCards = debitCards;
//        this.loans = loans;
//    }
//
//    public User(List<DebitCard> debitCards, List<Loan> loans) {
//        this.debitCards = debitCards;
//        this.loans = loans;
//    }
//
    public User(String name, String surname, String birthdate, String sex, String email, String password,  Role role, List<Status> statuses) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.sex = sex;
        this.email = email;
        this.password = password;
//        this.debitCards = debitCards;
//        this.loans = loans;
        this.role = role;
        this.statuses = statuses;
    }

    //    public User() {
//
//    }
//
//    @Override
//    public String toString() {
//        return "Name: " + name + '\'' +
//                ", surname: " + surname + '\'' +
//                ", birthdate: " + birthdate + '\'' +
//                ", sex: " + sex + '\'' +
//                ", email: " + email + '\'' +
//                ", password: " + password + '\'' +
//                "id: " + id;
//    }
//
//    public User(String name, String surname, String birthdate, String sex, String email, String password, List<DebitCard> debitCards, List<Loan> loans) {
//        this.name = name;
//        this.surname = surname;
//        this.birthdate = birthdate;
//        this.sex = sex;
//        this.email = email;
//        this.password = password;
//        this.debitCards = debitCards;
//        this.loans = loans;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
//
//    public String getBirthdate() {
//        return birthdate;
//    }
//
//    public void setBirthdate(String birthdate) {
//        this.birthdate = birthdate;
//    }
//
//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

}

