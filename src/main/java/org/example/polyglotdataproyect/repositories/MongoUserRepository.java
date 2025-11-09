package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoUserRepository extends MongoRepository<MongoUser, String> {

    Optional<MongoUser> findBySqlUserId(String sqlUserId);

    List<MongoUser> findByRole(String role);

    Optional<MongoUser> findByEmail(String email);
}
