package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Progress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends MongoRepository<Progress, String> {
    // Buscar progreso por username
    List<Progress> findByUsername(String username);

    // Buscar progreso por ID de rutina
    List<Progress> findByRoutineId(String routineId);

    // Buscar progreso por username y routineId
    List<Progress> findByUsernameAndRoutineId(String username, String routineId);
}
