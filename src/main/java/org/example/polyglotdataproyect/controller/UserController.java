package org.example.polyglotdataproyect.controller;

import org.example.polyglotdataproyect.dto.LoginRequest;
import org.example.polyglotdataproyect.entities.User;
import org.example.polyglotdataproyect.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Login básico para pruebas de lógica de negocio
     *
     * NOTA DE SEGURIDAD: Este login es solo para propósitos de desarrollo y pruebas.
     * En producción deberías:
     * 1. Usar BCryptPasswordEncoder para hashear contraseñas
     * 2. Implementar Spring Security
     * 3. Usar JWT o sesiones para autenticación
     * 4. Agregar protección CSRF
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());

        // Para desarrollo: comparación simple (NO SEGURA)
        // En producción: usar BCrypt.checkpw() o similar
        if (user != null && user.getIsActive() && user.getPasswordHash().equals(loginRequest.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("username", user.getUsername());
            response.put("role", user.getRole());
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // Obtener información del usuario
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }
}
