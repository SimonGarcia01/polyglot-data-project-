package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program,Integer> {
}
