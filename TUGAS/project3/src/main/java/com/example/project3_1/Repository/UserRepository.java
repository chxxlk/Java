package com.example.project3_1.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.project3_1.Model.User;

public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findByUsername(String username);

    public void deleteByUsername(String username);
}
