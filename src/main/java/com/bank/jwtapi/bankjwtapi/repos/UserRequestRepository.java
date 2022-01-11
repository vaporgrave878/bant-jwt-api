package com.bank.jwtapi.bankjwtapi.repos;

import com.bank.jwtapi.bankjwtapi.models.UserRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRequestRepository extends MongoRepository<UserRequest, String> {
    UserRequest findByUserEmail(String email);

    UserRequest findByIdAndUserEmail(String id, String email);

}
