package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Enrollment;
import org.example.polyglotdataproyect.entities.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
}
