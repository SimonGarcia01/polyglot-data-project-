package org.example.polyglotdataproyect.util;

import org.example.polyglotdataproyect.entities.User;
import org.example.polyglotdataproyect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Utilidad para obtener información del usuario actual
 * Como no usamos Spring Security, esto es un helper simple
 */
@Component
public class UserContext {

    @Autowired
    private UserRepository userRepository;

    /**
     * Obtiene el usuario desde la base de datos SQL por username
     */
    public User getUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }
        return userRepository.findByUsernameWithRelations(username);
    }

    /**
     * Verifica si el usuario tiene un rol específico
     */
    public boolean hasRole(String username, String role) {
        User user = getUserByUsername(username);
        return user != null && role.equalsIgnoreCase(user.getRole());
    }

    /**
     * Verifica si el usuario tiene alguno de los roles especificados
     */
    public boolean hasAnyRole(String username, String... roles) {
        User user = getUserByUsername(username);
        if (user == null) {
            return false;
        }

        for (String role : roles) {
            if (role.equalsIgnoreCase(user.getRole())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si el usuario es Admin
     */
    public boolean isAdmin(String username) {
        return hasRole(username, "ADMIN");
    }

    /**
     * Verifica si el usuario es Trainer
     */
    public boolean isTrainer(String username) {
        return hasRole(username, "TRAINER");
    }

    /**
     * Verifica si el usuario es Student
     */
    public boolean isStudent(String username) {
        return hasRole(username, "STUDENT");
    }

    /**
     * Verifica si el usuario es Employee
     */
    public boolean isEmployee(String username) {
        return hasRole(username, "EMPLOYEE");
    }
}
