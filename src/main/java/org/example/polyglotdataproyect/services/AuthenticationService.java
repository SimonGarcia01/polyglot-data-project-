package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.MongoUser;
import org.example.polyglotdataproyect.entities.User;
import org.example.polyglotdataproyect.repositories.MongoUserRepository;
import org.example.polyglotdataproyect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoUserRepository mongoUserRepository;

    public User login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getIsActive() && user.getPasswordHash().equals(rawPassword)) {
            // Sincronizar o crear usuario en MongoDB si no existe
            syncUserWithMongo(user);
            return user;
        }
        return null;
    }

    private void syncUserWithMongo(User sqlUser) {
        // Buscar si el usuario ya existe en MongoDB por sql_user_id
        String sqlUserId = extractSqlUserId(sqlUser);
        Optional<MongoUser> existingMongoUser = mongoUserRepository.findBySqlUserId(sqlUserId);

        if (existingMongoUser.isEmpty()) {
            // Si no existe, crear el usuario en MongoDB
            MongoUser mongoUser = new MongoUser();
            mongoUser.setSqlUserId(sqlUserId);
            mongoUser.setName(sqlUser.getUsername());
            mongoUser.setRole(mapRoleToMongoRole(sqlUser.getRole()));

            // Intentar obtener email si está disponible
            if (sqlUser.getStudent() != null && sqlUser.getStudent().getEmail() != null) {
                mongoUser.setEmail(sqlUser.getStudent().getEmail());
            } else if (sqlUser.getEmployee() != null && sqlUser.getEmployee().getEmail() != null) {
                mongoUser.setEmail(sqlUser.getEmployee().getEmail());
            }

            mongoUser.setCreatedAt(new Date());
            mongoUserRepository.save(mongoUser);
        }
    }

    private String extractSqlUserId(User sqlUser) {
        // Extraer el ID del usuario desde Student o Employee
        if (sqlUser.getStudent() != null) {
            return sqlUser.getStudent().getId();
        } else if (sqlUser.getEmployee() != null) {
            return sqlUser.getEmployee().getId();
        }
        return null;
    }

    private String mapRoleToMongoRole(String sqlRole) {
        // Mapear los roles de SQL a los roles de MongoDB
        switch (sqlRole.toUpperCase()) {
            case "ADMIN":
            case "TRAINER":
                return "trainer";
            case "STUDENT":
                return "student";
            case "EMPLOYEE":
                return "collaborator";
            default:
                return "student";
        }
    }
}
