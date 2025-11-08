package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Routine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineRepository extends MongoRepository<Routine, String> {
    // Buscar rutinas por username
    List<Routine> findByUsername(String username);

    // Buscar rutinas predefinidas
    List<Routine> findByIsPredefined(boolean isPredefined);

    // Buscar rutinas creadas por un entrenador
    List<Routine> findByCreatedByTrainer(String trainerUsername);
}
