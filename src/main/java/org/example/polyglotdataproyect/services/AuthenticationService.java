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
        User user = userRepository.findByUsernameWithRelations(username);
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

        // Validar que sqlUserId no sea null
        if (sqlUserId == null) {
            System.err.println("No se pudo extraer sqlUserId para el usuario: " + sqlUser.getUsername());
            return;
        }

        Optional<MongoUser> existingMongoUser = mongoUserRepository.findBySqlUserId(sqlUserId);

        if (existingMongoUser.isEmpty()) {
            // Si no existe, crear el usuario en MongoDB
            MongoUser mongoUser = new MongoUser();
            mongoUser.setSqlUserId(sqlUserId);
            mongoUser.setName(sqlUser.getUsername());
            mongoUser.setRole(mapRoleToMongoRole(sqlUser.getRole()));

            // Intentar obtener email si está disponible
            String email = null;
            if (sqlUser.getStudent() != null && sqlUser.getStudent().getEmail() != null) {
                email = sqlUser.getStudent().getEmail();
            } else if (sqlUser.getEmployee() != null && sqlUser.getEmployee().getEmail() != null) {
                email = sqlUser.getEmployee().getEmail();
            }

            // Usar username como email por defecto si no se encuentra
            if (email == null || email.isEmpty()) {
                email = sqlUser.getUsername() + "@default.com";
            }
            mongoUser.setEmail(email);

            mongoUser.setCreatedAt(new Date());
            mongoUserRepository.save(mongoUser);
        }
    }

    public String extractSqlUserId(User sqlUser) {
        // Extraer el ID del usuario desde Student o Employee
        if (sqlUser.getStudent() != null) {
            return sqlUser.getStudent().getId();
        } else if (sqlUser.getEmployee() != null) {
            return sqlUser.getEmployee().getId();
        }
        // Si no hay Student ni Employee, usar el username como ID
        return sqlUser.getUsername();
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
