package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty,Integer> {
}
