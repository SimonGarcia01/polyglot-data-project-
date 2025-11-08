package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
}
