package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.MongoUser;
import org.example.polyglotdataproyect.repositories.MongoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MongoUserService {

    @Autowired
    private MongoUserRepository mongoUserRepository;

    public List<MongoUser> getAllUsers() {
        return mongoUserRepository.findAll();
    }

    public Optional<MongoUser> getUserById(String id) {
        return mongoUserRepository.findById(id);
    }

    public Optional<MongoUser> getUserBySqlUserId(String sqlUserId) {
        return mongoUserRepository.findBySqlUserId(sqlUserId);
    }

    public List<MongoUser> getUsersByRole(String role) {
        return mongoUserRepository.findByRole(role);
    }

    public List<MongoUser> getAllTrainers() {
        return mongoUserRepository.findByRole("trainer");
    }

    public List<MongoUser> getAllStudents() {
        return mongoUserRepository.findByRole("student");
    }

    public List<MongoUser> getAllCollaborators() {
        return mongoUserRepository.findByRole("collaborator");
    }

    public MongoUser createUser(MongoUser user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(new Date());
        }
        return mongoUserRepository.save(user);
    }

    public MongoUser updateUser(String id, MongoUser user) {
        if (mongoUserRepository.existsById(id)) {
            user.setId(id);
            return mongoUserRepository.save(user);
        }
        return null;
    }

    public void deleteUser(String id) {
        mongoUserRepository.deleteById(id);
    }

    public Optional<MongoUser> getUserByEmail(String email) {
        return mongoUserRepository.findByEmail(email);
    }
}
