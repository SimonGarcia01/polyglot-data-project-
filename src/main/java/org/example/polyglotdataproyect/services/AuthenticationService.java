package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.User;
import org.example.polyglotdataproyect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    public User login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getIsActive() && user.getPasswordHash().equals(rawPassword)) {
            return user;
        }
        return null;
    }
}
