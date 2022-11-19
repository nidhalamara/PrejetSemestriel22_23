package com.example.backend.Configurations;

import com.example.backend.Models.Users.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UsersMongoRepo extends MongoRepository<User, String> {
    @Query("{email:'?0'}")
    User findUserByEmail(String email);
}
