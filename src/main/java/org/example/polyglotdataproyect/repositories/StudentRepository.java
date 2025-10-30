package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,String> {
}
