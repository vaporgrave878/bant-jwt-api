package com.bank.jwtapi.bankjwtapi.repos;

import com.bank.jwtapi.bankjwtapi.models.Role;
import com.bank.jwtapi.bankjwtapi.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);

    List<User> findAllByRole(Role role);
}
