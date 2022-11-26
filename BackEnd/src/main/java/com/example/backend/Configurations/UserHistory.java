package com.example.backend.Configurations;

import com.example.backend.Operation.Operation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserHistory extends MongoRepository<Operation,String> {
}
