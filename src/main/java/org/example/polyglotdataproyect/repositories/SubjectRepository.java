package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject,String> {
}
