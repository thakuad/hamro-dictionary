package com.hamrodictionary.repository;


import com.hamrodictionary.model.RoleModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<RoleModel, String> {
  RoleModel findByName(String name);
}
