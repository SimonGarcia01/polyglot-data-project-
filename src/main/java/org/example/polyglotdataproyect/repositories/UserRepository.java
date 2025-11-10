package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.student LEFT JOIN FETCH u.employee WHERE u.username = :username")
    User findByUsernameWithRelations(@Param("username") String username);
}
