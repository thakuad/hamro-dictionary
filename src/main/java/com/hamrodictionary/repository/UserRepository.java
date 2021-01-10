package com.hamrodictionary.repository;

import java.util.Optional;

import com.hamrodictionary.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel, String> {
  Optional<UserModel> findByUsername(String username);
  Boolean existsByUsername(String username);
  Boolean existsByEmailAddress(String emailAddress);
}
