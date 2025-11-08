package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Exercise;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends MongoRepository<Exercise, String> {
    // Buscar ejercicios predefinidos
    List<Exercise> findByIsPredefined(boolean isPredefined);

    // Buscar ejercicios por tipo
    List<Exercise> findByType(String type);

    // Buscar ejercicios creados por un usuario específico
    List<Exercise> findByCreatedByUsername(String username);
}
