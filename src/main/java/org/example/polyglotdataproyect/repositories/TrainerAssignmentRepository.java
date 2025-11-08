package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.TrainerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerAssignmentRepository extends JpaRepository<TrainerAssignment, Long> {
    // Buscar asignaciones activas de un entrenador
    List<TrainerAssignment> findByTrainerUsernameAndIsActive(String trainerUsername, boolean isActive);

    // Buscar asignación activa de un estudiante
    Optional<TrainerAssignment> findByStudentUsernameAndIsActive(String studentUsername, boolean isActive);

    // Buscar todas las asignaciones de un estudiante
    List<TrainerAssignment> findByStudentUsername(String studentUsername);

    // Buscar todas las asignaciones de un entrenador
    List<TrainerAssignment> findByTrainerUsername(String trainerUsername);
}
